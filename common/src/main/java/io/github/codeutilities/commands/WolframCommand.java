package io.github.codeutilities.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import io.github.codeutilities.CodeUtilities;
import io.github.codeutilities.commands.sys.Command;
import io.github.codeutilities.util.ChatUtil;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import net.minecraft.commands.SharedSuggestionProvider;

public class WolframCommand implements Command {

    private final String prefix = "§x§f§1§6§d§3§aWolframAlpha: ";
    private final String color = "§x§f§1§6§d§3§a";

    @Override
    public void register(CommandDispatcher<SharedSuggestionProvider> dispatcher) {
        dispatcher.register(CommandHandler.literal("wolfram")
            .then(CommandHandler.argument("expression", StringArgumentType.greedyString())
                .executes(ctx -> {
                    String expression = StringArgumentType.getString(ctx, "expression");

                    ChatUtil.displayClientMessage(prefix + "Calculating...");

                    String encoded = URLEncoder.encode(expression, StandardCharsets.UTF_8);

                    CodeUtilities.EXECUTOR.submit(() -> {
                        try {
                            HttpClient client = HttpClient.newHttpClient();
                            HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("https://www.wolframalpha.com/input/api/v1/code"))
                                .build();

                            String res = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

                            JsonObject obj = CodeUtilities.GSON.fromJson(res, JsonObject.class);
                            String proxyCode = obj.get("code").getAsString();

                            request = HttpRequest.newBuilder()
                                .uri(URI.create("https://www.wolframalpha.com/input/json.jsp?output=json&input=" + encoded + "&proxycode=" + proxyCode))
                                .header("Referer", "https://www.wolframalpha.com/")
                                .build();

                            res = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
                            obj = CodeUtilities.GSON.fromJson(res, JsonObject.class);

                            obj = obj.get("queryresult").getAsJsonObject();

                            JsonArray pods = obj.get("pods").getAsJsonArray();

                            String input = null, output = null;

                            for (JsonElement pod : pods) {
                                JsonObject podObj = pod.getAsJsonObject();
                                String id = podObj.get("id").getAsString();
                                if (id.equals("Input")) {
                                    input = podObj.get("subpods").getAsJsonArray().get(0).getAsJsonObject().get("plaintext").getAsString();
                                }
                                if (id.equals("Result")) {
                                    output = podObj.get("subpods").getAsJsonArray().get(0).getAsJsonObject().get("plaintext").getAsString();
                                }
                            }

                            if (input == null || output == null) {
                                ChatUtil.displayClientMessage(prefix + "Error");
                            } else {
                                ChatUtil.displayClientMessage(prefix);
                                ChatUtil.displayClientMessage(color + "In: " + input);
                                ChatUtil.displayClientMessage(color + "Out: " + output);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ChatUtil.displayClientMessage(prefix + "Error");
                        }
                    });

                    return CommandHandler.CANCEL_MESSAGE;
                })
            )
        );
    }

    @Override
    public String getName() {
        return "/wolfram";
    }

    @Override
    public String getDescription() {
        return "Calculate something with WolframAlpha";
    }
}