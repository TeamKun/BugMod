package net.kunmc.lab.bugmod.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
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

    public static int time = 0;
    public static List<Point> point = new ArrayList<Point>();
    public static final Identifier SPIDER1 = new Identifier(BugMod.MODID, "textures/entity/spider1.png");
    public static final Identifier SPIDER2 = new Identifier(BugMod.MODID, "textures/entity/spider2.png");

    public static void startBugs(int initTime){
        point.clear();
        point.add(new Point(0,-10));
        point.add(new Point(10,-60));
        point.add(new Point(-10,-110));
        time = initTime;
    }

    public static void renderBugs(MatrixStack matrixStack){
        if (time % 2 == 0) {
            MinecraftClient.getInstance().getTextureManager().bindTexture(SPIDER1);
        } else {
            MinecraftClient.getInstance().getTextureManager().bindTexture(SPIDER2);
        }
        for (Point p: point) {
            DrawableHelper.drawTexture(matrixStack, p.x,p.y,1.0f,1.0f, MinecraftClient.getInstance().getWindow().getWidth(),50,50,50);
            p.setLocation(p.x,p.y+10);
        }
    }
    public static void updateTimer(){
        if (time > 0){
            time--;
        }
        if (time <= 0){
           endBugs();
        }
   }
    public static void endBugs(){
        point.clear();
    }
    public static void register(){
        ClientTickEvents.START_CLIENT_TICK.register(m -> {
            updateTimer();
        });
    }
}
