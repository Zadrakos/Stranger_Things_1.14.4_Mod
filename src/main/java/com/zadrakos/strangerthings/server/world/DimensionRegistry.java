package com.zadrakos.strangerthings.server.world;

public class DimensionRegistry {
    private static final Map<Integer, STDimension> DIMENSIONS = new HashMap<>();

    public static final DimensionType UPSIDE_DOWN = DimensionRegistry.create(new UpsideDownDimension());
    public static final DimensionType MENTAL_VOID = DimensionRegistry.create(new MentalVoidDimension());

    public static void register() {
        try {
            for (Field field : DimensionRegistry.class.getDeclaredFields()) {
                Object value = field.get(null);
                if (value instanceof DimensionType) {
                    DimensionType dimensionType = (DimensionType) value;
                    DimensionManager.registerDimension(dimensionType.getId(), dimensionType);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static STDimension get(int dimension) {
        return DIMENSIONS.get(dimension);
    }

    private static DimensionType create(STDimension dimension) {
        int id = dimension.getDefaultID();
        DimensionType type = DimensionType.register(dimension.getName(), dimension.getName(), id, dimension.getWorldProvider(), true);
        DIMENSIONS.put(id, dimension);
        return type;
    }
}