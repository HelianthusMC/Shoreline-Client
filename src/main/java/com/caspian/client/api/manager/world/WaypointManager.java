package com.caspian.client.api.manager.world;

import com.caspian.client.api.waypoint.Waypoint;
import io.netty.util.internal.ConcurrentSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author linus
 * @since 1.0
 *
 * @see Waypoint
 */
public class WaypointManager
{
    //
    private final Set<Waypoint> waypoints = new ConcurrentSet<>();

    /**
     *
     *
     * @param waypoint
     */
    public void register(Waypoint waypoint)
    {
        waypoints.add(waypoint);
    }

    /**
     *
     *
     * @param waypoints
     */
    public void register(Waypoint... waypoints)
    {
        for (Waypoint waypoint : waypoints)
        {
            register(waypoint);
        }
    }

    /**
     *
     * @param waypoint
     * @return
     */
    public boolean remove(Waypoint waypoint)
    {
        return waypoints.remove(waypoint);
    }

    /**
     *
     * @param waypoint
     * @return
     */
    public boolean remove(String waypoint)
    {
        for (Waypoint w : getWaypoints())
        {
            if (w.getName().equalsIgnoreCase(waypoint))
            {
                return waypoints.remove(w);
            }
        }
        return false;
    }

    /**
     *
     *
     * @return
     */
    public Collection<Waypoint> getWaypoints()
    {
        return waypoints;
    }

    /**
     *
     *
     * @return
     */
    public Collection<String> getIps()
    {
        final Set<String> ips = new HashSet<>();
        for (Waypoint waypoint : getWaypoints())
        {
            ips.add(waypoint.getIp());
        }
        return ips;
    }
}
