package es.upm.miw.apaw_ep_festivals.spectator_data;

import java.util.ArrayList;
import java.util.List;

public class SpectatorComposite implements SpectatorComponent {

    private String name;

    private List<SpectatorComponent> spectators;

    public SpectatorComposite(String name) {
        this.name = name;
        this.spectators = new ArrayList<>();
    }

    @Override
    public void add(SpectatorComponent spectatorComponent) {
        if(spectatorComponent == null){
            throw new IllegalArgumentException("Invalid spectator");
        }
        this.spectators.add(spectatorComponent);
    }

    @Override
    public void remove(SpectatorComponent spectatorComponent) {
        if(spectatorComponent == null){
            throw new IllegalArgumentException("Invalid spectator");
        }
        this.spectators.remove(spectatorComponent);
    }

    @Override
    public String info() {
        StringBuilder info = new StringBuilder();
        info.append("Group name: ").append(name).append("\\n\\t spectators:\\n");
        for (SpectatorComponent spectator : this.spectators) {
            info.append("\\t\\t").append(spectator.info()).append("\\n");
        }
        return info.toString();
    }
}
