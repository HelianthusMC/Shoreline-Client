package com.caspian.client.api.manager;

import com.caspian.client.Caspian;
import com.caspian.client.api.module.Module;
import com.caspian.client.api.module.ToggleModule;
import com.caspian.client.impl.module.client.ClickGuiModule;
import com.caspian.client.impl.module.combat.AutoCrystalModule;
import com.caspian.client.impl.module.movement.SprintModule;
import com.caspian.client.init.Managers;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class ModuleManager
{
    //
    private final Map<String, Module> modules =
            Collections.synchronizedMap(new HashMap<>());

    /**
     * Initializes the module register.
     */
    public ModuleManager()
    {
        // MAINTAIN ALPHABETICAL ORDER
        register(
                // Client
                new ClickGuiModule(),

                // Combat
                new AutoCrystalModule(),

                // Exploit

                // Movement
                new SprintModule()

                // Render

                // World
        );

        Caspian.info("Registered {} modules", modules.size());
    }

    /**
     * Registers extra module information
     */
    public void postInit()
    {
        for (Module module : modules.values())
        {
            if (!(module instanceof ToggleModule toggleModule))
            {
                continue;
            }

            Managers.MACRO.register(toggleModule.getKeybinding());
        }
    }

    /**
     *
     *
     * @param modules
     *
     * @see #register(Module)
     */
    private void register(Module... modules)
    {
        for (Module module : modules)
        {
            register(module);
        }
    }

    /**
     *
     *
     * @param module
     */
    private void register(Module module)
    {
        modules.put(module.getId(), module);
    }

    /**
     *
     *
     * @param id
     * @return
     */
    public Module getModule(String id)
    {
        return modules.get(id);
    }

    /**
     *
     *
     * @return
     */
    public Collection<Module> getModules()
    {
        return modules.values();
    }
}