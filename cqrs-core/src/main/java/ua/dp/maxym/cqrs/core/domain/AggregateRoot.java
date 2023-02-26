package ua.dp.maxym.cqrs.core.domain;

import lombok.extern.slf4j.Slf4j;
import ua.dp.maxym.cqrs.core.events.BaseEvent;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class AggregateRoot {
    protected String id;
    private int version = -1;
    private final List<BaseEvent> changes = new ArrayList<>();

    public String getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<BaseEvent> getUncommittedChanges() {
        return changes;
    }

    public void markChangesAsCommitted() {
        this.changes.clear();
    }

    protected void applyChange(BaseEvent event, boolean isNewEvent) {
        try {
            var method = getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(event);
        } catch (NoSuchMethodException e) {
            log.atWarn().log("The apply method was not found in the aggregate for {}", event.getClass().getName());
        } catch (Exception e) {
            log.atError().log("Error applying event to aggregate {}", e);
        } finally {
            if (isNewEvent) changes.add(event);
        }
    }

    public void raiseEvent(BaseEvent event) {
        applyChange(event, true);
    }

    public void replayEvents(Iterable<BaseEvent> events) {
        events.forEach(event -> applyChange(event, false));
    }
}
