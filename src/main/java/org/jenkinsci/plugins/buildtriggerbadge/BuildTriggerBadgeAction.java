package org.jenkinsci.plugins.buildtriggerbadge;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.model.BuildBadgeAction;
import hudson.model.Cause;
import jenkins.model.Jenkins;

/**
 * Badge action of the build trigger cause.
 *
 * @author Michael Pailloncy
 */
public class BuildTriggerBadgeAction implements BuildBadgeAction {

    private final Cause cause;

    /**
     * Initialize causes of the build.
     */
    public BuildTriggerBadgeAction(@NonNull Cause cause) {
        this.cause = cause;
    }

    public String getTooltip() {
        return cause.getShortDescription();
    }

    // used by jelly
    public static BuildTriggerBadgePlugin getPlugin() {
        return Jenkins.get().getPlugin(BuildTriggerBadgePlugin.class);
    }

    // non use interface methods
    public String getIconFileName() {
        return null;
    }

    public String getDisplayName() {
        return "Trigger " + cause.getClass().getSimpleName() + " : " + getTooltip();
    }

    public String getUrlName() {
        return "";
    }

    /**
     * Returns the icon to be used as a badge for the given cause that triggered the associated build.
     *
     * @return the associated icon for the given cause.
     */
    public String getIcon() {
        return new IconFinder(cause).find();
    }
}
