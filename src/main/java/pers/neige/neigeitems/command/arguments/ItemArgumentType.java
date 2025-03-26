package pers.neige.neigeitems.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.neige.neigeitems.command.selector.ItemSelector;
import pers.neige.neigeitems.item.ItemGenerator;
import pers.neige.neigeitems.manager.ItemManager;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * NI物品生成器参数类型
 */
public class ItemArgumentType implements ArgumentType<ItemSelector> {
    private static final Collection<String> EXAMPLES = Collections.singletonList("ExampleItem");

    private ItemArgumentType() {
    }

    @NotNull
    public static ItemArgumentType item() {
        return new ItemArgumentType();
    }

    @Nullable
    public static ItemGenerator getItem(
            @NotNull CommandContext<CommandSender> context,
            @NotNull String name
    ) {
        return getItemSelector(context, name).select(context);
    }

    @NotNull
    public static ItemSelector getItemSelector(
            @NotNull CommandContext<CommandSender> context,
            @NotNull String name
    ) {
        return context.getArgument(name, ItemSelector.class);
    }

    @NotNull
    @Override
    public ItemSelector parse(
            @NotNull StringReader reader
    ) {
        return new ItemSelector(reader);
    }

    @NotNull
    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(
            @NotNull CommandContext<S> context,
            @NotNull SuggestionsBuilder builder
    ) {
        String lowerCaseRemaining = builder.getRemaining().toLowerCase();
        ItemManager.INSTANCE.itemIds().forEach((id) -> {
            if (id.toLowerCase().startsWith(lowerCaseRemaining)) {
                builder.suggest(id);
            }
        });
        return builder.buildFuture();
    }

    @NotNull
    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
