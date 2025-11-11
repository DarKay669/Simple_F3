package fr.darkay.simplef3.common.register;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventRegister
{
    private Minecraft mc = Minecraft.getMinecraft();
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Pre event)
    {
        if (event.type == ElementType.DEBUG)
        {
            GL11.glPushMatrix();
            
            this.renderDebugInfoLeft();
            this.renderDebugInfoRight(new ScaledResolution(this.mc));
            
            GL11.glPopMatrix();
            
            event.setCanceled(true);
        }
    }
    
    private void renderDebugInfoLeft()
    {
        List<String> list = this.getDebugInfoLeft();
        
        for (int i = 0; i < list.size(); i++)
        {
            String s = (String) list.get(i);
            
            if (!Strings.isNullOrEmpty(s))
            {
                int j = this.mc.fontRendererObj.FONT_HEIGHT;
                int k = this.mc.fontRendererObj.getStringWidth(s);
                int l = 2;
                int i1 = 2 + j * i;
                
                Gui.drawRect(1, i1 - 1, 2 + k + 1, i1 + j - 1, -1873784752);
                
                this.mc.fontRendererObj.drawString(s, 2, i1, 14737632);
            }
        }
    }
    
    private void renderDebugInfoRight(ScaledResolution scaledResolution)
    {
        List<String> list = this.getDebugInfoRight();
        
        for (int i = 0; i < list.size(); i++)
        {
            String s = (String) list.get(i);
            
            if (!Strings.isNullOrEmpty(s))
            {
                int j = this.mc.fontRendererObj.FONT_HEIGHT;
                int k = this.mc.fontRendererObj.getStringWidth(s);
                int l = scaledResolution.getScaledWidth() - 2 - k;
                int i1 = 2 + j * i;
                
                Gui.drawRect(l - 1, i1 - 1, l + k + 1, i1 + j - 1, -1873784752);
                
                this.mc.fontRendererObj.drawString(s, l, i1, 14737632);
            }
        }
    }
    
    private List<String> getDebugInfoLeft()
    {
        String[] directions = {"SOUTH", "WEST", "NORTH", "EAST"};
        
        BiomeGenBase biomeGenBase = this.mc.theWorld.getBiomeGenForCoords(new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ));
        
        List<String> list = Lists.newArrayList(new String[] {"Minecraft 1.8.9", this.mc.debug, "", String.format("XYZ: %.3f / %.3f / %.3f", new Object[] {Double.valueOf(this.mc.getRenderViewEntity().posX), Double.valueOf(this.mc.getRenderViewEntity().getEntityBoundingBox().minY), Double.valueOf(this.mc.getRenderViewEntity().posZ)}), "Direction: " + directions[(MathHelper.floor_double(this.mc.thePlayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3)], "", "Biome: " + biomeGenBase.biomeName + " (" + biomeGenBase.biomeID + ")"});
        
        return list;
    }
    
    private List<String> getDebugInfoRight()
    {
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalFreeMemory = totalMemory - freeMemory;
        
        List<String> list = Lists.newArrayList(new String[] {String.format("Java: %s", new Object[] {System.getProperty("java.version")}), "Mem: " + totalFreeMemory * 100L / maxMemory + "% " + freeMemory / 1024L / 1024L + "/" + maxMemory / 1024L / 1024L + "MB", "Allocated: " + totalMemory * 100L / maxMemory + "% " + totalMemory / 1024L / 1024L + "MB", GL11.glGetString(GL11.GL_RENDERER), "", "Block: (" + (int) this.mc.getRenderViewEntity().posX + " " + (int) this.mc.getRenderViewEntity().getEntityBoundingBox().minY + " " + (int) this.mc.getRenderViewEntity().posZ + ")", "Light: " + EnumChatFormatting.RED + this.mc.theWorld.getLightFor(EnumSkyBlock.BLOCK, new BlockPos(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().getEntityBoundingBox().minY, this.mc.getRenderViewEntity().posZ))});
        
        return list;
    }
}