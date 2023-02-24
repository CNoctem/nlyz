package xyz.sis.chess.nlyz.msg;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public enum ActionBus {

    BUS {
        public void register(Class<? extends Action> actionType, ActionReceiver receiver) {
            REGISTRY.computeIfAbsent(actionType, k -> new HashSet<>()).add(receiver);
        }

        public void fireAction(Action a) {
            log.debug("Action (" + a.getClass() + ") fired.");
            var set = REGISTRY.get(a.getClass());
            if (set != null){
                for (var r : set) r.receive(a);
            }
        }
    };

    public abstract void fireAction(Action a);
    public abstract void register(Class<? extends Action> actionType, ActionReceiver receiver);

    private static final Map<Class<? extends Action>, Set<ActionReceiver>> REGISTRY = new HashMap<>();
    private static final Logger log = LogManager.getLogger(ActionBus.class);
}
