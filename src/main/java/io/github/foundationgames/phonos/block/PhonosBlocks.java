package io.github.foundationgames.phonos.block;

import io.github.foundationgames.phonos.Phonos;
import io.github.foundationgames.phonos.block.entity.RadioJukeboxBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

public class PhonosBlocks {

    public static final Block LOUDSPEAKER = register(new LoudspeakerBlock(FabricBlockSettings.copy(Blocks.JUKEBOX)), "loudspeaker");
    public static final Block RADIO_JUKEBOX = register(new RadioJukeboxBlock(FabricBlockSettings.copy(Blocks.JUKEBOX)), "radio_jukebox");
    public static final Block RADIO_NOTE_BLOCK = register(new RadioNoteBlock(FabricBlockSettings.copy(Blocks.JUKEBOX)), "radio_note_block");

    public static final Block GOURD_SPEAKER = registerExtra(new LoudspeakerBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.ORANGE).strength(1.0F).sounds(BlockSoundGroup.WOOD)), "gourd_speaker");
    public static final Block SPEAK_O_LANTERN = registerExtra(new LoudspeakerBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.ORANGE).strength(1.0F).sounds(BlockSoundGroup.WOOD).luminance(state -> 15)), "speak_o_lantern");
    public static final Block TINY_POTATO_SPEAKER = registerExtra(new PotatoSpeakerBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.CLEAR).strength(0.3F).sounds(BlockSoundGroup.WOOD)), "tiny_potato_speaker");

    public static final Block OXIDIZED_COPPER_SPEAKER = new OxidizableCopperSpeakerBlock(FabricBlockSettings.copy(Blocks.OXIDIZED_COPPER), Oxidizable.OxidizationLevel.OXIDIZED);
    public static final Block WEATHERED_COPPER_SPEAKER = new OxidizableCopperSpeakerBlock(FabricBlockSettings.copy(Blocks.WEATHERED_COPPER), Oxidizable.OxidizationLevel.WEATHERED);
    public static final Block EXPOSED_COPPER_SPEAKER = new OxidizableCopperSpeakerBlock(FabricBlockSettings.copy(Blocks.EXPOSED_COPPER), Oxidizable.OxidizationLevel.EXPOSED);
    public static final Block COPPER_SPEAKER = new OxidizableCopperSpeakerBlock(FabricBlockSettings.copy(Blocks.COPPER_BLOCK), Oxidizable.OxidizationLevel.UNAFFECTED);

    public static final Block WAXED_COPPER_SPEAKER = new LoudspeakerBlock(FabricBlockSettings.copy(Blocks.WAXED_COPPER_BLOCK));
    public static final Block WAXED_EXPOSED_COPPER_SPEAKER = new LoudspeakerBlock(FabricBlockSettings.copy(Blocks.WAXED_EXPOSED_COPPER));
    public static final Block WAXED_WEATHERED_COPPER_SPEAKER = new LoudspeakerBlock(FabricBlockSettings.copy(Blocks.WAXED_WEATHERED_COPPER));
    public static final Block WAXED_OXIDIZED_COPPER_SPEAKER = new LoudspeakerBlock(FabricBlockSettings.copy(Blocks.WAXED_OXIDIZED_COPPER));


    public static BlockEntityType<RadioJukeboxBlockEntity> RADIO_JUKEBOX_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, Phonos.id("radio_jukebox"), FabricBlockEntityTypeBuilder.create(RadioJukeboxBlockEntity::new, RADIO_JUKEBOX).build(null));

    private static Block register(Block block, String name) {
        Registry.register(Registry.ITEM, Phonos.id(name), new BlockItem(block, new Item.Settings().group(Phonos.PHONOS_ITEM_GROUP)));
        return Registry.register(Registry.BLOCK, Phonos.id(name), block);
    }

    private static Block registerExtra(Block block, String name) {
        Registry.register(Registry.ITEM, Phonos.id(name), new BlockItem(block, new Item.Settings().group(Phonos.PHONOS_EXTRAS_GROUP)));
        return Registry.register(Registry.BLOCK, Phonos.id(name), block);
    }

    public static void init() {
        registerExtra(COPPER_SPEAKER, "copper_speaker");
        registerExtra(EXPOSED_COPPER_SPEAKER, "exposed_copper_speaker");
        registerExtra(WEATHERED_COPPER_SPEAKER, "weathered_copper_speaker");
        registerExtra(OXIDIZED_COPPER_SPEAKER, "oxidized_copper_speaker");
        registerExtra(WAXED_COPPER_SPEAKER, "waxed_copper_speaker");
        registerExtra(WAXED_EXPOSED_COPPER_SPEAKER, "waxed_exposed_copper_speaker");
        registerExtra(WAXED_WEATHERED_COPPER_SPEAKER, "waxed_weathered_copper_speaker");
    }
}
