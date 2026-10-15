package net.the_last_sword.compat.lucky_block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.the_last_sword.init.ModBlockEntities;
import com.geckolib.animatable.GeoBlockEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.RawAnimation;
import com.geckolib.util.GeckoLibUtil;

//终焉幸运方块实体 - 承载GeckoLib循环动画 + luck值NBT持久化
public class TheLastEndLuckyBlockEntity extends BlockEntity implements GeoBlockEntity {

    private static final String TAG_LUCK = "Luck";
    private static final String TAG_WELL_VARIANT = "WellVariant";

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    //幸运方块循环动画（环旋转 + 光柱脉动）
    private static final RawAnimation LOOP = RawAnimation.begin().thenLoop("0");

    //幸运值：正偏向好事件，负偏向坏事件
    private int luck = 0;
    //井变体标记：true 时触发 LuckyWellTriggerEvent，抽一个非放井事件并在原位放信标
    private boolean wellVariant = false;

    public TheLastEndLuckyBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.THE_LAST_END_LUCKY_BLOCK.get(), pos, state);
    }

    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public boolean isWellVariant() {
        return wellVariant;
    }

    public void setWellVariant(boolean wellVariant) {
        this.wellVariant = wellVariant;
    }

    //放置时从ItemStack的NBT读取数据
    public void readFromItemTag(CompoundTag tag) {
        if (tag.contains(TAG_LUCK)) {
            this.luck = tag.getIntOr(TAG_LUCK, 0);
        }
        if (tag.contains(TAG_WELL_VARIANT)) {
            this.wellVariant = tag.getBooleanOr(TAG_WELL_VARIANT, false);
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt(TAG_LUCK, luck);
        if (wellVariant) output.putBoolean(TAG_WELL_VARIANT, true);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.luck = input.getIntOr(TAG_LUCK, 0);
        this.wellVariant = input.getBooleanOr(TAG_WELL_VARIANT, false);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>("controller", 0, state -> {
            state.setAnimation(LOOP);
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
