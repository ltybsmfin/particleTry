package com.example.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;

import java.util.logging.Logger;

@Environment(EnvType.CLIENT)
public class ParticleEvents {
    private static int triggerCount = 0;

    public static void register() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack stack = player.getStackInHand(hand);

            //testGroupParticle testparticle = new testGroupParticle()
            System.out.println("你使用了木棍");
            // 检查是否为木棍且是主手
            if (stack.getItem() == Items.STICK && hand == Hand.MAIN_HAND) {
                triggerCount++;
                if (world.isClient()) { // 客户端生成粒子
                    Vec3d pos = player.getPos();
                    for (int i = 0; i < 10; i++) {
                        world.addParticle(
                                ModParticleTypes.TEST_GROUP_PARTICLE,
                                pos.x,
                                pos.y + 1.0, // 在玩家头顶生成
                                pos.z,
                                (Math.random() - 0.5) * 0.2,
                                (Math.random() - 0.5) * 0.2,
                                (Math.random() - 0.5) * 0.2
                        );
                    }
                }
                // 返回 SUCCESS 阻止后续事件处理（可选）
                return TypedActionResult.success(stack);
            }
            return TypedActionResult.pass(stack);
        });
    }

    public static int getTriggerCount() {
        return triggerCount;
    }

}
