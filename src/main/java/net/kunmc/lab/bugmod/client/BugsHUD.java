package net.kunmc.lab.bugmod.client;

import net.kunmc.lab.bugmod.BugMod;
import net.kunmc.lab.bugmod.BugModClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BugsHUD {

    public static List<Point> point = new ArrayList<Point>();

    public static void startBugs(){
        point.add(new Point(0,-10));
        point.add(new Point(10,-60));
        point.add(new Point(-10,-110));
    }

    public static void renderBugs(MatrixStack matrixStack){
        if (UpdateClientLevelManager.time % 2 == 0) {
            MinecraftClient.getInstance().getTextureManager().bindTexture(BugModClient.SPIDER1);
        } else {
            MinecraftClient.getInstance().getTextureManager().bindTexture(BugModClient.SPIDER2);
        }
        for (Point p: point) {
            DrawableHelper.drawTexture(matrixStack, p.x,p.y,1.0f,1.0f, MinecraftClient.getInstance().getWindow().getWidth(),50,50,50);
            p.setLocation(p.x,p.y+10);
        }
    }
}
