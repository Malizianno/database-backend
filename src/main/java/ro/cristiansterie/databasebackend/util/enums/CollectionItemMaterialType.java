package ro.cristiansterie.databasebackend.util.enums;

import lombok.Getter;

@Getter
public enum CollectionItemMaterialType {
	ALUMINIUM("Aluminium"),
	ALUMINIUM_BRONZE("Aluminium-Bronze"),
	ALPACA("Alpaca"),
	BRASS("Brass"),
	BRASS_PLATED_COPPER("Brass-plated copper"),
	BRONZE("Bronze"),
	BRONZE_BRASS("Bronze (Brass?)"),
	BIMETALLIC_CENTRE_BRASS_RING_CUPRO_NICKEL_ZINC("Bimetallic: centre brass, ring cupro-nickel-zinc"),
	BIMETALLIC_CENTRE_CUPRO_NICKEL_RING_BRASS("Bimetallic: centre cupro-nickel, ring brass"),
	BIMETALLIC_CENTRE_BRONZE_RING_ACMONITAL("Bimetallic: centre bronze, ring acmonital"),
	BIMETALLIC_CENTRE_BRONZE_RING_NICKEL_PLATED_STEEL("Bimetallic: centre bronze, ring nickel-plated steel"),
	BIMETALLIC_CENTRE_GOLD_RING_CUPRO_NICKEL("Bimetallic: centre gold, ring cupro-nickel"),
	BIMETALLIC_CENTRE_NICKEL_BRASS_RING_CUPRO_NICKEL("Bimetallic: centre nickel-brass, ring cupro-nickel"),
	BIMETALLIC_CENTRE_NICKEL_BRASS_RING_CUPRO_ZINC("Bimetallic: centre nickel-brass, ring cupro-zinc"),
	BIMETALLIC_CENTRE_NICKEL_PLATED_BRASS_RING_CUPRO_NICKEL("Bimetallic: centre nickel-plated brass, ring cupro-nickel"),
	BIMETALLIC_CENTRE_NICKEL_PLATED_BRONZE_RING_CUPRO_NICKEL("Bimetallic: centre nickel-plated bronze, ring cupro-nickel"),
	BIMETALLIC_CENTRE_STAINLESS_STEEL_RING_CUPRO_ALUMINIUM_NICKEL("Bimetallic: centre stainless steel, ring cupro-aluminium-nickel"),
	BIMETALLIC_CENTRE_STEEL_RING_CUPRO_NICKEL("Bimetallic: centre steel, ring cupro-nickel"),
	BIMETALLIC_CENTRE_STEEL_RING_NICKEL_PLATED_STEEL("Bimetallic: centre steel, ring nickel-plated steel"),
	BIMETALLIC_CENTRE_BRASS_PLATED_STEEL_RING_NICKEL_PLATED_STEEL("Bimetallic: centre brass-plated steel, ring nickel-plated steel"),
	BIMETALLIC_CENTRE_BRONZE_RING_BRASS("Bimetallic: centre bronze, ring brass"),
	BIMETALLIC_CENTRE_CUPRO_NICKEL_RING_BRONZE("Bimetallic: centre cupro-nickel, ring bronze"),
	BIMETALLIC_CENTRE_NICKEL_BRASS_RING_BRASS("Bimetallic: centre nickel-brass, ring brass"),
	BIMETALLIC_CENTRE_STEEL_RING_STAINLESS_STEEL("Bimetallic: centre steel, ring stainless steel"),
	COPPER("Copper"),
	COPPER_NICKEL("Copper-nickel"),
	COPPER_NICKEL_ZINC("Copper-nickel-zinc"),
	COPPER_PLATED_NICKEL_BRASS("Copper-plated nickel-brass"),
	COPPER_PLATED_CUPRO_NICKEL("Copper-plated copper-nickel"),
	COPPER_ZINC("Copper-zinc"),
	GOLD_NORDIC_GOLD("Gold (Nordic gold)"),
	HYBRID_SUBSTRATE("Hybrid substrate (75% cotton, 25% linen)"),
	MANGANESE_BRASS("Manganese-brass"),
	NICKEL("Nickel"),
	NICKEL_BRASS("Nickel-brass"),
	NICKEL_BRONZE("Nickel-bronze"),
	NICKEL_PLATED_COPPER("Nickel-plated copper"),
	NICKEL_PLATED_STEEL("Nickel-plated steel"),
	NICKEL_PLATED_BRASS("Nickel-plated brass"),
	PAPER("Paper"),
	POLYMER("Polymer"),
	SILVER("Silver (0.4 / 0.800 / 0.835 / 0.925 gold-plated)"),
	STAINLESS_STEEL("Stainless steel"),
	STEEL_PLATED_COPPER("Steel-plated copper"),
	ZINC("Zinc"),
	ZINC_PLATED_COPPER("Zinc-plated copper"),
	UNKNOWN("-");

	private final String label;

	CollectionItemMaterialType(String label) {
		this.label = label;
	}
}
