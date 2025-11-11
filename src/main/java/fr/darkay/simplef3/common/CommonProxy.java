package fr.darkay.simplef3.common;

import fr.darkay.simplef3.SimpleF3;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class CommonProxy
{
    public void init(FMLInitializationEvent event)
    {
        SimpleF3.proxy.registerHandler();
    }
    
    public void registerHandler() {}
}