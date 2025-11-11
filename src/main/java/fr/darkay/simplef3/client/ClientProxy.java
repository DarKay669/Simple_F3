package fr.darkay.simplef3.client;

import fr.darkay.simplef3.common.CommonProxy;
import fr.darkay.simplef3.common.register.EventRegister;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    public ClientProxy()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    public void registerHandler()
    {
        MinecraftForge.EVENT_BUS.register(new EventRegister());
        FMLCommonHandler.instance().bus().register(new EventRegister());
        
        super.registerHandler();
    }
}