package net.the_last_sword.entity;

import net.minecraft.resources.Identifier;
import net.the_last_sword.TheLastSwordMod;

public enum TheLastEndSwordWraithAppearance {
    DEFAULT("the_last_end_sword_wraith", "gui.the_last_sword.sword_wraith_appearance.default",
            "entity.the_last_sword.the_last_end_sword_wraith"),
    THYSIA_TELENDRACON("thysia_telendracon", "gui.the_last_sword.sword_wraith_appearance.thysia_telendracon",
            "entity.the_last_sword.thysia_telendracon");

    private final String id;
    private final String nameKey;
    private final String entityNameKey;
    private final Identifier model;
    private final Identifier animation;
    private final Identifier texture;

    TheLastEndSwordWraithAppearance(String id, String nameKey, String entityNameKey) {
        this.id = id;
        this.nameKey = nameKey;
        this.entityNameKey = entityNameKey;
        this.model = Identifier.fromNamespaceAndPath(TheLastSwordMod.MOD_ID, id);
        this.animation = Identifier.fromNamespaceAndPath(TheLastSwordMod.MOD_ID, id);
        this.texture = Identifier.fromNamespaceAndPath(TheLastSwordMod.MOD_ID, "textures/entities/" + id + ".png");
    }

    public String getId() {
        return id;
    }

    public String getNameKey() {
        return nameKey;
    }

    public String getEntityNameKey() {
        return entityNameKey;
    }

    public Identifier getModel() {
        return model;
    }

    public Identifier getAnimation() {
        return animation;
    }

    public Identifier getTexture() {
        return texture;
    }

    public TheLastEndSwordWraithAppearance previous() {
        TheLastEndSwordWraithAppearance[] appearances = values();
        return appearances[Math.floorMod(ordinal() - 1, appearances.length)];
    }

    public TheLastEndSwordWraithAppearance next() {
        TheLastEndSwordWraithAppearance[] appearances = values();
        return appearances[(ordinal() + 1) % appearances.length];
    }

    public static TheLastEndSwordWraithAppearance fromId(String id) {
        for (TheLastEndSwordWraithAppearance appearance : values()) {
            if (appearance.id.equals(id)) {
                return appearance;
            }
        }
        return DEFAULT;
    }
}
