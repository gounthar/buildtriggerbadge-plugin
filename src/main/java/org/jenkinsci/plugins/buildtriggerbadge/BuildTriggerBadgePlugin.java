package org.jenkinsci.plugins.buildtriggerbadge;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Plugin;
import hudson.PluginWrapper;
import hudson.model.Descriptor.FormException;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest2;

/**
 * Plugin extension.
 *
 * @author Michael Pailloncy
 */
public class BuildTriggerBadgePlugin extends Plugin {

    private static final Logger LOGGER = Logger.getLogger(BuildTriggerBadgePlugin.class.getSimpleName());

    public static final String FIELD_ACTIVATED = "buildtriggerbadge_activated";

    /** Indicates if the plugin is activated. */
    private boolean activated;

    public BuildTriggerBadgePlugin() {
        this(true);
    }

    @DataBoundConstructor
    public BuildTriggerBadgePlugin(boolean activated) {
        this.activated = activated;
    }

    @NonNull
    public static PluginWrapper get() {
        final PluginWrapper plugin = Jenkins.get().getPluginManager().getPlugin(BuildTriggerBadgePlugin.class);
        if (plugin == null) {
            LOGGER.log(Level.SEVERE, "Could not find the descriptor of the *current* plugin, something is very wrong");
            throw new IllegalStateException("");
        }
        return plugin;
    }

    @Override
    public void configure(StaplerRequest2 req, JSONObject formData)
            throws IOException, ServletException, FormException {

        super.configure(req, formData);
        // get activated value from system configuration save.
        this.setActivated(formData.getBoolean(FIELD_ACTIVATED));
        // serialize to XML
        this.save();
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
