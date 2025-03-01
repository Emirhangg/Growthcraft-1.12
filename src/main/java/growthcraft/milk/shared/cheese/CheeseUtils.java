package growthcraft.milk.shared.cheese;

import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.definition.IObjectVariant;
import growthcraft.milk.shared.Reference;
import growthcraft.milk.shared.definition.EnumCheeseStage;

public class CheeseUtils {
	public static final int MAX_CUTS = 1 << 2;
	
	public static <T extends IObjectVariant> int getItemMetaFor(T cheese, int slices, EnumCheeseStage atStage) {
		// first 3 bits reserved for stage.
		// second 2 bits reserved for cutting.
		// The rest is for the variant.
		if( slices > MAX_CUTS )
			throw new IllegalArgumentException("Maximal 4 slices are supported.");
		if( slices <= 0 ) {
			slices = 1;
			GrowthcraftLogger.getLogger(Reference.MODID).warn("Cheese item meta for invalid count of 0 slices requested.");
		}
		if( atStage.ordinal() > 7 )
			throw new IllegalArgumentException("Maximal 8 stages are supported.");
		return (cheese.getVariantID() << 5) | ((slices-1) << 3) | atStage.ordinal();
	}
	
	public static int getVariantIDFromMeta(int meta) {
		return meta >> 5;
	}

	public static int getTopSlicesFromMeta(int meta) {
		return ((meta >> 3) & 0x3) + 1;
	}
	
	public static EnumCheeseStage getStageFromMeta(int meta) {
		return EnumCheeseStage.values()[meta & 0x7];
	}
}
