package jcraft.rebrain.navigable;

import net.minecraft.server.v1_8_R1.ControllerMove;
import net.minecraft.server.v1_8_R1.EntityInsentient;
import net.minecraft.server.v1_8_R1.GenericAttributes;
import net.minecraft.server.v1_8_R1.MathHelper;

import org.bukkit.craftbukkit.v1_8_R1.TrigMath;

public class CustomSimpleControllerMove extends ControllerMove {

    private double lastDiff = 0D;

    public CustomSimpleControllerMove(EntityInsentient entityinsentient) {
        super(entityinsentient);

        this.a = entityinsentient;
        this.b = entityinsentient.locX;
        this.c = entityinsentient.locY;
        this.d = entityinsentient.locZ;
    }

    public void c() {
        this.a.m(0.0F);

        if (this.f) {
            this.f = false;
            int i = MathHelper.floor(this.a.getBoundingBox().b + 0.5D);

            double xDiff = this.b - this.a.locX;
            double zDiff = this.d - this.a.locZ;
            double yDiff = this.c - i;

            double diff = xDiff * xDiff + yDiff * yDiff + zDiff * zDiff;

            if (diff >= 2.500000277905201E-007D) {
                float direction = (float) (TrigMath.atan2(zDiff, xDiff) * 180.0D / 3.141592741012573D) - 90.0F;

                this.a.yaw = a(this.a.yaw, direction, 30.0F);
                this.a.j((float) (this.e * this.a.getAttributeInstance(GenericAttributes.d).getValue()));

                double plainDiff = xDiff * xDiff + zDiff * zDiff;
                double testDiff = lastDiff - plainDiff;

                if (((yDiff > 0.0D) && (plainDiff < 1.0D)) || (testDiff > 0.0D && testDiff < 0.1D)) {
                    this.a.getControllerJump().a();
                }

                lastDiff = plainDiff;
            }
        }
    }

}
