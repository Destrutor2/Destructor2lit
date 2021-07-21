package com.destructor.destructor2lit.enums;

import com.destructor.destructor2lit.guis.BwItemType;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public enum BwItem {
    Wool(Material.WOOL, 16, 4, "Wool", Material.IRON_INGOT, com.destructor.destructor2lit.guis.BwItemType.Blocks, Arrays.asList("Great for bridging across", "islands. Turns into your team's", "color.")), Clay(Material.STAINED_CLAY, 16, 12, "Hardened Clay", Material.IRON_INGOT, com.destructor.destructor2lit.guis.BwItemType.Blocks, Arrays.asList("Basic block to defend your bed.")), BlastProofGlass(Material.GLASS, 4, 12, "Blast-Proof Glass", Material.IRON_INGOT, com.destructor.destructor2lit.guis.BwItemType.Blocks, Arrays.asList("Immune to explosions.")), EndStone(Material.ENDER_STONE, 12, 24, "End Stone", Material.IRON_INGOT, com.destructor.destructor2lit.guis.BwItemType.Blocks, Arrays.asList("Solid block to defend your bed.")), Ladder(Material.LADDER, 16, 4, "Ladder", Material.IRON_INGOT, com.destructor.destructor2lit.guis.BwItemType.Blocks, Arrays.asList("Useful to save cats stuck in", "trees.")), Wood(Material.WOOD, 16, 4, "Oak Wood Planks", Material.GOLD_INGOT, com.destructor.destructor2lit.guis.BwItemType.Blocks, Arrays.asList("Good block to defend your bed.", "Strong against pickaxes.")), Obsidian(Material.OBSIDIAN, 4, 4, "Obsidian", Material.EMERALD, com.destructor.destructor2lit.guis.BwItemType.Blocks, Arrays.asList(("Extreme protection for your bed."))),
    StoneSword(Material.STONE_SWORD, 10, "Stone Sword", Material.IRON_INGOT, com.destructor.destructor2lit.guis.BwItemType.Melee), IronSword(Material.IRON_SWORD, 7, "Iron Sword", Material.GOLD_INGOT, com.destructor.destructor2lit.guis.BwItemType.Melee), DiamondSword(Material.DIAMOND_SWORD, 4, "Diamond Sword", Material.EMERALD, com.destructor.destructor2lit.guis.BwItemType.Melee), KbStick(Material.STICK, 5, "Stick (Knockback I)", Material.GOLD_INGOT, com.destructor.destructor2lit.guis.BwItemType.Melee),
    ChainmailArmor(Material.CHAINMAIL_BOOTS, 40, "Permanent Chainmail Armor", Material.IRON_INGOT, com.destructor.destructor2lit.guis.BwItemType.Armor, Arrays.asList("Chainmail leggings and boots", "which you will always spawn", "with.")), IronArmor(Material.IRON_BOOTS, 12, "Permanent Iron Armor", Material.GOLD_INGOT, com.destructor.destructor2lit.guis.BwItemType.Armor, Arrays.asList("Iron leggings and boots which", "you will always spawn with.")), DiamondArmor(Material.DIAMOND_BOOTS, 6, "Permanent Diamond Armor", Material.EMERALD, com.destructor.destructor2lit.guis.BwItemType.Armor, Arrays.asList("Diamond leggings and boots which", "you will always crush with.")),
    Pickaxe(true, com.destructor.destructor2lit.guis.BwItemType.Tools, Arrays.asList("This is an upgradable item.", "It will lose 1 tier upon", "death!", "", "You will permanently", "respawn with at least the", "lower tier.")), Axe(true, com.destructor.destructor2lit.guis.BwItemType.Tools, Arrays.asList("This is an upgradable item.", "It will lose 1 tier upon", "death!", "", "You will permanently", "respawn with at least the", "lower tier.")), Sheers(Material.SHEARS, 20, "Shears", Material.IRON_INGOT, com.destructor.destructor2lit.guis.BwItemType.Tools, Arrays.asList("Great to get rid of wool. You","will always spawn with these","shears.")),
    Arrow(Material.ARROW, 8, 2, "Arrow", Material.GOLD_INGOT, com.destructor.destructor2lit.guis.BwItemType.Ranged), Bow(Material.BOW, 1, 12, "Bow", Material.GOLD_INGOT, com.destructor.destructor2lit.guis.BwItemType.Ranged), PowerBow(Material.BOW, 1, 24, "Bow (Power I)", Material.GOLD_INGOT, com.destructor.destructor2lit.guis.BwItemType.Ranged), PunchBow(Material.BOW, 1, 6, "Bow (Power I, Punch I)", Material.EMERALD, com.destructor.destructor2lit.guis.BwItemType.Ranged),
    JumpPot(1, "Jump V Potion (45 seconds)", com.destructor.destructor2lit.guis.BwItemType.Potions, Arrays.asList("Jump Boost V (0:45)"), Material.EMERALD), SpeedPot(1, "Speed II Potion (45 seconds)", com.destructor.destructor2lit.guis.BwItemType.Potions, Arrays.asList("Speed II (0:45)"), Material.EMERALD), InvisPot(2, "Invisibility Potion (30 seconds)", com.destructor.destructor2lit.guis.BwItemType.Potions, Arrays.asList("Complete Invisibility (0:30)"), Material.EMERALD),
    GoldApple(Material.GOLDEN_APPLE, 3, "Golden Apple", Material.GOLD_INGOT, com.destructor.destructor2lit.guis.BwItemType.Utility, Arrays.asList("Well-rounded healing.")), BedBug(Material.SNOW_BALL, 40, "Bedbug", Material.IRON_INGOT, com.destructor.destructor2lit.guis.BwItemType.Utility, Arrays.asList("Spawns silverfish where the", "snowball lands to distract your", "enemies. Lasts 15 seconds.")), IronGolem(120, "Dream Defender", Material.IRON_INGOT, com.destructor.destructor2lit.guis.BwItemType.Utility, Arrays.asList("Iron Golem to help defend your", "base. Lasts 4 minutes.")), Fireball(Material.FIREBALL, 40, "Fireball", Material.IRON_INGOT, com.destructor.destructor2lit.guis.BwItemType.Utility, Arrays.asList("Right-click to launch! Great to", "knock back enemies walking on", "thin bridges.")), Tnt(Material.TNT, 4, "TNT", Material.GOLD_INGOT, com.destructor.destructor2lit.guis.BwItemType.Utility, Arrays.asList("Instantly ignites, appropriate", "to explode things!")), EnderPearl(Material.ENDER_PEARL, 4, "Ender Pearl", Material.EMERALD, com.destructor.destructor2lit.guis.BwItemType.Utility, Arrays.asList("The quickest way to invade enemy", "bases.")), Water(Material.WATER_BUCKET, 4, "Water Bucket", Material.GOLD_INGOT, com.destructor.destructor2lit.guis.BwItemType.Utility, Arrays.asList("Great to slow down approaching", "enemies. Can also protect", "against TNT.")), BridgeEgg(Material.EGG, 2, "Bridge Egg", Material.EMERALD, com.destructor.destructor2lit.guis.BwItemType.Utility, Arrays.asList("This egg creates a bridge in its", "trail after being thrown.")), Milk(Material.MILK_BUCKET, 4, "Magic Milk", Material.GOLD_INGOT, com.destructor.destructor2lit.guis.BwItemType.Utility, Arrays.asList("Avoid triggering traps for 60", "seconds after consuming.")), Sponge(Material.SPONGE, 4,3, "Sponge", Material.GOLD_INGOT, com.destructor.destructor2lit.guis.BwItemType.Utility, Arrays.asList("Great for soaking up water."));

    private Material ItemMaterial = Material.BARRIER;
    private int ItemCount = 1;
    private int ItemCost = 0;
    private String ItemName = "DEFAULT NAME! YOU NEED TO CHANGE THIS SHIT";
    private Material ItemCostMaterial = Material.IRON_INGOT;
    private com.destructor.destructor2lit.guis.BwItemType BwItemType = com.destructor.destructor2lit.guis.BwItemType.Default;
    private List<String> ItemLore = Arrays.asList("Null");
    private Boolean isTiered = false;

    BwItem(int ItemCost, String ItemName, BwItemType BwItemType, List<String> ItemLore, Material ItemCostMaterial) {
        this.ItemCost = ItemCost;
        this.ItemName = ItemName;
        this.BwItemType = BwItemType;
        this.ItemLore = ItemLore;
        this.ItemCostMaterial = ItemCostMaterial;
    }

    BwItem(Material ItemMaterial, int ItemCount, int ItemCost, String ItemName, Material ItemCostMaterial, BwItemType BwItemType, List<String> ItemLore) {
        this.ItemMaterial = ItemMaterial;
        this.ItemCount = ItemCount;
        this.ItemCost = ItemCost;
        this.ItemName = ItemName;
        this.ItemCostMaterial = ItemCostMaterial;
        this.BwItemType = BwItemType;
        this.ItemLore = ItemLore;
    }

    BwItem(int ItemCost, String ItemName, Material ItemCostMaterial, BwItemType BwItemType, List<String> ItemLore) {
        this.ItemCost = ItemCost;
        this.ItemName = ItemName;
        this.ItemCostMaterial = ItemCostMaterial;
        this.BwItemType = BwItemType;
        this.ItemLore = ItemLore;
    }

    BwItem(Material ItemMaterial, int ItemCost, String ItemName, Material ItemCostMaterial, BwItemType BwItemType, List<String> ItemLore) {
        this.ItemMaterial = ItemMaterial;
        this.ItemCost = ItemCost;
        this.ItemName = ItemName;
        this.ItemCostMaterial = ItemCostMaterial;
        this.BwItemType = BwItemType;
        this.ItemLore = ItemLore;
    }

    BwItem(Boolean isTiered, BwItemType BwItemType, List<String> ItemLore) {
        this.isTiered = isTiered;
        this.BwItemType = BwItemType;
        this.ItemLore = ItemLore;
    }

    BwItem(Material ItemMaterial, int ItemCount, int ItemCost, String ItemName, Material ItemCostMaterial, BwItemType BwItemType) {
        this.ItemMaterial = ItemMaterial;
        this.ItemCount = ItemCount;
        this.ItemCost = ItemCost;
        this.ItemName = ItemName;
        this.ItemCostMaterial = ItemCostMaterial;
        this.BwItemType = BwItemType;
    }

    BwItem(Material ItemMaterial, int ItemCost, String ItemName, Material ItemCostMaterial, BwItemType BwItemType) {
        this.ItemMaterial = ItemMaterial;
        this.ItemCost = ItemCost;
        this.ItemName = ItemName;
        this.ItemCostMaterial = ItemCostMaterial;
        this.BwItemType = BwItemType;
    }


    public int getItemCount() {
        return ItemCount;
    }

    public int getItemCost() {
        return ItemCost;
    }

    public String getItemName() {
        return ItemName;
    }

    public Material getItemCostMaterial() {
        return ItemCostMaterial;
    }

    public com.destructor.destructor2lit.guis.BwItemType getBwItemType() {
        return BwItemType;
    }

    public Material getItemMaterial() {
        return ItemMaterial;
    }

    public List<String> getItemLore() {
        return ItemLore;
    }

    public Boolean isTiered() {
        return isTiered;
    }
}


