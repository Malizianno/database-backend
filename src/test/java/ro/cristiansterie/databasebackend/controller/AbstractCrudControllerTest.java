package ro.cristiansterie.databasebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

abstract class AbstractCrudControllerTest {
	private static final ObjectMapper JSON = new ObjectMapper();
	private static final UUID ID = UUID.fromString("9c18b19e-1688-4729-9b39-b70711c65121");

	protected abstract ControllerCase controllerCase();

	@Test
	void crudRoutesDelegateToServiceAndReturnExpectedStatuses() throws Exception {
		ControllerCase testCase = controllerCase();
		Object service = mock(testCase.serviceType());
		Object controller = testCase.controllerType().getConstructor(testCase.serviceType()).newInstance(service);
		MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();
		Object requestDto = JSON.readValue(testCase.requestJson(), testCase.dtoType());
		String responseJson = JSON.writeValueAsString(requestDto);

		when(invoke(service, "findById", new Class<?>[]{UUID.class}, ID)).thenReturn(requestDto);
		when(invoke(service, "findAll", new Class<?>[]{})).thenReturn(Set.of(requestDto));
		when(invoke(service, "save", new Class<?>[]{testCase.dtoType()}, requestDto)).thenReturn(requestDto);
		when(invoke(service, "update", new Class<?>[]{UUID.class, testCase.dtoType()}, ID, requestDto))
				.thenReturn(requestDto);

		mvc.perform(get(testCase.basePath() + "/" + ID))
				.andExpect(status().isOk())
				.andExpect(content().json(responseJson));
		verifyCall(service, "findById", new Class<?>[]{UUID.class}, ID);

		mvc.perform(get(testCase.basePath() + "/"))
				.andExpect(status().isOk())
				.andExpect(content().json("[" + responseJson + "]"));
		verifyCall(service, "findAll", new Class<?>[]{});

		mvc.perform(post(testCase.basePath() + "/")
						.contentType("application/json")
						.content(testCase.requestJson()))
				.andExpect(status().isOk())
				.andExpect(content().json(responseJson));
		verifyCall(service, "save", new Class<?>[]{testCase.dtoType()}, requestDto);

		mvc.perform(put(testCase.basePath() + "/" + ID)
						.contentType("application/json")
						.content(testCase.requestJson()))
				.andExpect(status().isOk())
				.andExpect(content().json(responseJson));
		verifyCall(service, "update", new Class<?>[]{UUID.class, testCase.dtoType()}, ID, requestDto);

		mvc.perform(delete(testCase.basePath() + "/" + ID))
				.andExpect(status().isNoContent())
				.andExpect(content().string(""));
		verifyCall(service, "delete", new Class<?>[]{UUID.class}, ID);
	}

	private static Object invoke(Object target, String methodName, Class<?>[] parameterTypes, Object... arguments)
			throws Exception {
		Method method = target.getClass().getMethod(methodName, parameterTypes);
		return method.invoke(target, arguments);
	}

	private static void verifyCall(Object target, String methodName, Class<?>[] parameterTypes, Object... arguments)
			throws Exception {
		invoke(verify(target), methodName, parameterTypes, arguments);
	}

	protected record ControllerCase(
			String basePath,
			Class<?> controllerType,
			Class<?> serviceType,
			Class<?> dtoType,
			String requestJson
	) {
	}
}
