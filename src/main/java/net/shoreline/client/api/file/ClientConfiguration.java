package net.shoreline.client.api.file;

import net.shoreline.client.Shoreline;
import net.shoreline.client.api.account.AccountFile;
import net.shoreline.client.api.account.AccountType;
import net.shoreline.client.api.module.Module;
import net.shoreline.client.api.module.ModuleFile;
import net.shoreline.client.api.social.SocialRelation;
import net.shoreline.client.api.social.SocialFile;
import net.shoreline.client.api.waypoint.WaypointFile;
import net.shoreline.client.init.Managers;
import net.shoreline.client.init.Modules;
import net.shoreline.client.util.Globals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/**
 *
 *
 * @author linus
 * @since 1.0
 * 
 * @see ConfigFile
 */
public class ClientConfiguration implements Globals
{
    // Main client directory. This folder will contain all locally saved 
    // configurations for the client.
    private Path clientDir;
    // Set of configuration files that must be saved and loaded. This can be
    // modified after init.
    private final Set<ConfigFile> files = new HashSet<>();
    //
    private String configPreset;

    /**
     * 
     */
    public ClientConfiguration() 
    {
        if (mc == null) return; //fix crash -ZANE remove when u find the actual problem bcs that's like everywher
        final Path runningDir = mc.runDirectory.toPath();
        try
        {
            File homeDir = new File(System.getProperty("user.home"));
            clientDir = homeDir.toPath();
        }
        // will resort to running dir if client does not have access to the 
        // user home dir
        catch (Exception e)
        {
            Shoreline.error("Could not access home dir, defaulting to running dir");
            e.printStackTrace();
            clientDir = runningDir;
        }
        finally
        {
            // cannot write, minecraft always has access to the running dir
            if (clientDir == null || !Files.exists(clientDir)
                    || !Files.isWritable(clientDir))
            {
                clientDir = runningDir;
            }
            clientDir = clientDir.resolve("Shoreline");
            // create client directory
            if (!Files.exists(clientDir))
            {
                try
                {
                    Files.createDirectory(clientDir);
                }
                // write error
                catch (IOException e)
                {
                    Shoreline.error("Could not create client dir");
                    e.printStackTrace();
                }
            }
        }
        for (Module module : Managers.MODULE.getModules())
        {
            // files.add(new ModulePreset(clientDir.resolve("Defaults"), module));
            files.add(new ModuleFile(clientDir.resolve("Modules"), module));
        }
        files.add(Modules.INV_CLEANER.getBlacklistFile(clientDir));
        for (SocialRelation relation : SocialRelation.values())
        {
            files.add(new SocialFile(clientDir, relation));
        }
        for (AccountType accountType : AccountType.values())
        {
            files.add(new AccountFile(clientDir.resolve("Accounts"),
                    accountType));
        }
    }
    
    /**
     *
     */
    public void saveClient()
    {
        for (String ip : Managers.WAYPOINT.getIps())
        {
            files.add(new WaypointFile(clientDir.resolve("Waypoints"), ip));
        }
        for (ConfigFile file : files)
        {
            file.save();
        }
    }

    /**
     *
     */
    public void loadClient()
    {
        for (ConfigFile file : files)
        {
            file.load();
        }
    }

    public void setConfigPreset(String configPreset)
    {
        this.configPreset = configPreset;
    }

    /**
     *
     * @return
     */
    public Path getClientDirectory()
    {
        return clientDir;
    }
}
