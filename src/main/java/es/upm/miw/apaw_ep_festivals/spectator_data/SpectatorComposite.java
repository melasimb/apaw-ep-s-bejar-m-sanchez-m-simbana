package es.upm.miw.apaw_ep_festivals.spectator_data;

import java.util.ArrayList;
import java.util.List;

public class SpectatorComposite extends SpectatorComponent {

    private String name;

    private List<SpectatorComponent> spectators;

    public SpectatorComposite(String name) {
        this.name = name;
        this.spectators = new ArrayList<>();
    }

    @Override
    public void add(SpectatorComponent spectatorComponent) {
        assert spectatorComponent != null;
        this.spectators.add(spectatorComponent);
    }

    @Override
    public void remove(SpectatorComponent spectatorComponent) {
        assert spectatorComponent != null;
        this.spectators.remove(spectatorComponent);
    }

    @Override
    public String info() {
        String info = "Group name: " + name + "\\n\\t spectators:\\n";
        for (SpectatorComponent spectator : this.spectators) {
            info += "\\t\\t" + spectator.info() + "\\n";
        }
        return info;
    }
}
