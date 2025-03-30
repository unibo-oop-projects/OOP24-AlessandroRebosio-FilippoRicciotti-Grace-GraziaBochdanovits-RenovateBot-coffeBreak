package it.unibo.coffeBreak.model.score.api;

import java.util.List;

public interface Repository<X> {
    boolean save(List<X> list);

    List<X> load();
}
