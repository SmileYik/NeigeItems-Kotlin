package pers.neige.neigeitems.utils.pagination.impl.scroll;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.neige.neigeitems.utils.pagination.ScrollPager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class ListScrollPager<T> extends ScrollPager<T> {
    private final @NotNull List<T> handle;
    private final @NotNull AtomicInteger offset = new AtomicInteger(0);
    private final @Nullable Predicate<T> filter;

    public ListScrollPager(@NotNull List<T> handle, int pageSize, @Nullable Predicate<T> filter) {
        super(pageSize);
        this.handle = handle;
        this.filter = filter;
    }

    public @NotNull List<T> getHandle() {
        return handle;
    }

    @Override
    public void resetOffset() {
        offset.set(0);
    }

    @Override
    public void moveOffset(int delta) {
        offset.updateAndGet(current -> {
            int size = handle.size();
            if (size == 0) return 0;
            return Math.max(0, Math.min(current + delta, size));
        });
    }

    @Override
    public @NotNull List<T> getCurrentPageElements() {
        final int size = handle.size();
        if (size == 0) return Collections.emptyList();

        final int start = Math.min(offset.get(), size - 1);
        final int end = Math.min(start + pageSize, size);

        List<T> page = new ArrayList<>(pageSize);
        Iterator<T> it = handle.subList(start, end).iterator();

        while (it.hasNext() && page.size() < pageSize) {
            T element = it.next();
            if (filter == null || filter.test(element)) page.add(element);
        }
        if (offset.get() == 0) return page;

        if (page.size() < pageSize) {
            int delta = pageSize - page.size();
            List<T> list = handle.subList(Math.max(0, start - delta), start);

            for (int i = list.size() - 1; i >= 0; i--) {
                if (page.size() >= pageSize) break;
                T element = list.get(i);
                if (filter == null || filter.test(element)) page.add(0, element);
            }
        }
        return page;
    }

    @Override
    public int getTotalElements() {
        return handle.size();
    }

    @Override
    public boolean nextPage() {
        if (offset.get() >= (getTotalElements() - 1)) return false;
        moveOffset(getPageSize());
        return true;
    }

    @Override
    public boolean prevPage() {
        if (offset.get() <= 0) return false;
        moveOffset(-getPageSize());
        return true;
    }

    @Override
    public boolean hasNextPage() {
        return offset.get() < (getTotalElements() - 1);
    }

    @Override
    public boolean hasPrevPage() {
        return offset.get() > 0;
    }

    @Override
    public void toFinalOffset() {
        offset.set(getTotalElements() - 1);
    }
}
