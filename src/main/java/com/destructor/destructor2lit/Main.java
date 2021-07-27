package com.destructor.destructor2lit;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.destructor.destructor2lit.commands.bedwarsCommand;
import com.destructor.destructor2lit.commands.bedwarsCommandTab;
import com.destructor.destructor2lit.commands.toggleparticulesCommand;
import com.destructor.destructor2lit.customEntities.EntityTypes;
import com.destructor.destructor2lit.enums.GamePhase;
import com.destructor.destructor2lit.enums.GameState;
import com.destructor.destructor2lit.events.NpcClickEvent;
import com.destructor.destructor2lit.listeners.NpcClickListener;
import com.destructor.destructor2lit.listeners.PlayersListeners;
import com.destructor.destructor2lit.listeners.WorldListeners;
import com.destructor.destructor2lit.managers.NPCManager;
import com.destructor.destructor2lit.timers.StartTimer;
import com.destructor.destructor2lit.utils.*;
import com.destructor.destructor2lit.utils.packetWrappers.WrapperPlayClientUseEntity;
import com.destructor.destructor2lit.utils.packetWrappers.WrapperPlayServerEntityEquipment;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import de.slikey.effectlib.EffectManager;
import io.netty.util.AttributeKey;
import net.minecraft.server.v1_8_R3.Block;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

/*TODO LIST
-Implementer les items spéciaux bedwars:
-Ajouter les popup towers
-World borders: si x ou z > ou < a mainlobby x ou z +- limite
-creer une enum kill message
-colorer le nom de certains items (ex: kb sticks en bleu)
-creer un commande whitelist
-base protection, tout les blocks au allentour du spawn + n blocks seront supprimes
-island radius: pour traps et heal pool
-message lorsd'un changement de gamephase
-Tester le plugin en changeant le ping
-Bruit


-empecher de consommer des items quand joueur a un inventaire ouvert
-empecher d'acheter des items quand l'inventaire est rempli
-empecher de mettre des outils dans un coffre
-empecher d'ouvrir les chests des teams pas finals killed
-mettre un delay pour quand on achete qqlchose qui n'est pas des blocks
-créer les upgrades
-Gérer les wins
-après une win/tie, attendre 20s et reboot le serv
-créer un tab correct
-Créer une metadata "splittable" qu'on donne au ironingot et goldingot générés
-Lorsque ItemPickup, check si "splittable", si oui, donne à tout les joueur à 3blocks à la ronde l'item
-Faire des animations differentes par joueurs pour les eponges !1!111!!
-Faire des animations differentes pour les popup tower et les spawns de golem et de silverfish !!!

-Créer le lien avec le lobby
-Changer la map du lobby
-Faire un lobby correct
-Implementer les levels
-Ajouter plus de maps bw+voler les maps de bedwarspractice???
-ajouter un kill message avec compteur de final kills et lits
-implementer les limites de pickup d'items (par exemple on ne pickup plus de gold si on a plus d'1 stack dans l'inv)
-https://namelessmc.com/
-https://www.spigotmc.org/threads/tutorial-adding-custom-patches-to-spigot.158694/
-https://www.spigotmc.org/resources/randomkb.48891/
* */


/*18:11:39 ERROR]: Could not pass event NpcClickEvent to Destructor2lit vAlpha
org.bukkit.event.EventException
	at org.bukkit.plugin.java.JavaPluginLoader$1.execute(JavaPluginLoader.java:310) ~[spigot.jar:git-Spigot-db6de12-18fbb24]
	at org.bukkit.plugin.RegisteredListener.callEvent(RegisteredListener.java:62) ~[spigot.jar:git-Spigot-db6de12-18fbb24]
	at org.bukkit.plugin.SimplePluginManager.fireEvent(SimplePluginManager.java:502) [spigot.jar:git-Spigot-db6de12-18fbb24]
	at org.bukkit.plugin.SimplePluginManager.callEvent(SimplePluginManager.java:487) [spigot.jar:git-Spigot-db6de12-18fbb24]
	at com.destructor.destructor2lit.Main$1.onPacketReceiving(Main.java:172) [Destructor2lit.jar:?]
	at com.comphenix.protocol.injector.SortedPacketListenerList.invokeReceivingListener(SortedPacketListenerList.java:114) [ProtocolLib.jar:4.7.0]
	at com.comphenix.protocol.injector.SortedPacketListenerList.invokePacketRecieving(SortedPacketListenerList.java:67) [ProtocolLib.jar:4.7.0]
	at com.comphenix.protocol.injector.PacketFilterManager.handlePacket(PacketFilterManager.java:537) [ProtocolLib.jar:4.7.0]
	at com.comphenix.protocol.injector.PacketFilterManager.invokePacketRecieving(PacketFilterManager.java:509) [ProtocolLib.jar:4.7.0]
	at com.comphenix.protocol.injector.netty.ProtocolInjector.packetReceived(ProtocolInjector.java:360) [ProtocolLib.jar:4.7.0]
	at com.comphenix.protocol.injector.netty.ProtocolInjector.onPacketReceiving(ProtocolInjector.java:325) [ProtocolLib.jar:4.7.0]
	at com.comphenix.protocol.injector.netty.ChannelInjector.decode(ChannelInjector.java:593) [ProtocolLib.jar:4.7.0]
	at io.netty.handler.codec.ByteToMessageDecoder.callDecode(ByteToMessageDecoder.java:249) [spigot.jar:git-Spigot-db6de12-18fbb24]
	at io.netty.handler.codec.ByteToMessageDecoder.channelRead(ByteToMessageDecoder.java:149) [spigot.jar:git-Spigot-db6de12-18fbb24]
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:333) [spigot.jar:git-Spigot-db6de12-18fbb24]
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:319) [spigot.jar:git-Spigot-db6de12-18fbb24]
	at com.comphenix.protocol.injector.netty.ChannelInjector$2.channelRead(ChannelInjector.java:289) [ProtocolLib.jar:4.7.0]
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:333) [spigot.jar:git-Spigot-db6de12-18fbb24]
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:319) [spigot.jar:git-Spigot-db6de12-18fbb24]
	at io.netty.handler.codec.ByteToMessageDecoder.channelRead(ByteToMessageDecoder.java:163) [spigot.jar:git-Spigot-db6de12-18fbb24]
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:333) [spigot.jar:git-Spigot-db6de12-18fbb24]
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:319) [spigot.jar:git-Spigot-db6de12-18fbb24]
	at io.netty.handler.timeout.ReadTimeoutHandler.channelRead(ReadTimeoutHandler.java:150) [spigot.jar:git-Spigot-db6de12-18fbb24]
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:333) [spigot.jar:git-Spigot-db6de12-18fbb24]
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:319) [spigot.jar:git-Spigot-db6de12-18fbb24]
	at io.netty.channel.DefaultChannelPipeline.fireChannelRead(DefaultChannelPipeline.java:787) [spigot.jar:git-Spigot-db6de12-18fbb24]
	at io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:130) [spigot.jar:git-Spigot-db6de12-18fbb24]
	at io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:511) [spigot.jar:git-Spigot-db6de12-18fbb24]
	at io.netty.channel.nio.NioEventLoop.processSelectedKeysPlain(NioEventLoop.java:430) [spigot.jar:git-Spigot-db6de12-18fbb24]
	at io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:384) [spigot.jar:git-Spigot-db6de12-18fbb24]
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:354) [spigot.jar:git-Spigot-db6de12-18fbb24]
	at io.netty.util.concurrent.SingleThreadEventExecutor$2.run(SingleThreadEventExecutor.java:116) [spigot.jar:git-Spigot-db6de12-18fbb24]
	at java.base/java.lang.Thread.run(Thread.java:831) [?:?]
Caused by: net.minecraft.server.v1_8_R3.CancelledPacketHandleException*/



public class Main extends JavaPlugin {
	private final BlockOverride stainedglass = new BlockOverride(Block.getById(95));
	private final List<Player> players = new ArrayList<>();
	public StartTimer startTimer = new StartTimer(this, 20);
	public List<String> colors = new ArrayList<>();
	public int buildlimit;
	private GameState gameState;
	private GamePhase gamePhase;
	private Boolean[] beds;
	private Boolean[] healPools;
	private final List<TeamGen> gens = new ArrayList<>();
	public NPCManager npcManager;
	public Location spawn;
	public Map<UUID, ItemStack[]> offlinePlayersInventory = new HashMap<>();
	public Map<UUID, ItemStack[]> offlinePlayersArmor = new HashMap<>();
	public Map<UUID, ItemStack[]> offlinePlayersEnderItems = new HashMap<>();
	public int globalTimer;
	public Map<UUID, Location> npcLocations = new HashMap<>();
	private final List<Player> hiddenPlayers = new ArrayList<>();
	public float minBowCharge;
	public EffectManager effectManager;
	public List<Player> noAnimationPlayers = new ArrayList<>();
	public int maxGoldDelay;
	public int minGoldDelay;
	public int maxIronDelay;
	public int ticksToTry;
	public int builddownlimit;
	public long attackTagMilis;
	public int voidHeight;
	public double golemDamageMultiplier;
	public ProtocolManager protocolManager;
	private Utils utils = new Utils();
	public EntityHider entityHider;
	public int popupTowerSpeedMultiplier;


	@Override
	public void onLoad() {
		stainedglass.set("durability", 2f * 1000);
	}

	@Override
	public void onEnable() {
//        On crée le config.yml par default au cas ou il n'y est pas
		saveDefaultConfig();

		Bukkit.clearRecipes();

//		new NMSUtils().registerEntity("AngryGolem",99, EntityIronGolem.class,AngryIronGolem.class);

//        On verifie qu'il y a holographic display
		if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
			getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
			getLogger().severe("*** Destructor2lit will be disabled. ***");
			getPluginLoader().disablePlugin(this);
			this.setEnabled(false);
			return;
		}

//		if(!Bukkit.getPluginManager().isPluginEnabled("EffectLib")){
//			getLogger().severe("*** EffectLib is not installed or not enabled. ***");
//			getLogger().severe("*** Destructor2lit will be disabled. ***");
//			this.setEnabled(false);
//			return;
//		}

		try {
			effectManager = new EffectManager(this);
		} catch (NoClassDefFoundError e) {
			getPluginLoader().disablePlugin(this);
			this.setEnabled(false);
			return;
		}

		npcManager = new NPCManager();
		protocolManager = ProtocolLibrary.getProtocolManager();
		Main main = this;

//		###############################################################
//		C'est pas top de l'avoir compresse comme ca mais bon
		protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, WrapperPlayClientUseEntity.TYPE) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
				if (event.getPacketType() == WrapperPlayClientUseEntity.TYPE) {
					WrapperPlayClientUseEntity wrapperevent = new WrapperPlayClientUseEntity(event.getPacket());
					if (wrapperevent.getType().equals(EnumWrappers.EntityUseAction.ATTACK) || wrapperevent.getType().equals(EnumWrappers.EntityUseAction.INTERACT)) {
						NpcClickEvent npcClickEvent = new NpcClickEvent(event.getPlayer(), wrapperevent.getTargetID(), main);
						Bukkit.getPluginManager().callEvent(npcClickEvent);
					}
//					Bukkit.broadcastMessage(wrapperevent.getTargetID()+" a ete clicke. les upgrade sont: "+main.npcManager.upgrades+". shops: "+main.npcManager.shops);
				}
			}
		});

//		Si un joueur est invis, quand le serveur envois les packets de son armor on lui enleve
		protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, WrapperPlayServerEntityEquipment.TYPE) {
			@Override
			public void onPacketSending(PacketEvent event) {
				if (event.getPacketType().equals(WrapperPlayServerEntityEquipment.TYPE)) {
					WrapperPlayServerEntityEquipment wrapperPlayServerEntityEquipment = new WrapperPlayServerEntityEquipment(event.getPacket());
					if (wrapperPlayServerEntityEquipment.getEntity(Bukkit.getWorlds().get(0)) instanceof Player)
						if (((Player) wrapperPlayServerEntityEquipment.getEntity(Bukkit.getWorlds().get(0))).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
							List<Material> armor = new ArrayList<>(Arrays.asList(
									Material.LEATHER_BOOTS,
									Material.LEATHER_LEGGINGS,
									Material.LEATHER_CHESTPLATE,
									Material.LEATHER_HELMET,
									Material.CHAINMAIL_BOOTS,
									Material.CHAINMAIL_LEGGINGS,
									Material.IRON_BOOTS,
									Material.IRON_LEGGINGS,
									Material.DIAMOND_BOOTS,
									Material.DIAMOND_LEGGINGS));
							if (armor.contains(wrapperPlayServerEntityEquipment.getItem().getType()))
								wrapperPlayServerEntityEquipment.setItem(null);
						}
				}
			}
		});

//		On affiche pas les armor stands serverOnly pour ca on utilise du code vole lol
//		entityHider = new EntityHider(this, EntityHider.Policy.BLACKLIST);


//		###############################################################
		players.addAll(Bukkit.getOnlinePlayers());
		npcManager.showNpcs(this, players);


//		On mets toutes les sections de Spawns dans colors
		colors.addAll(getConfig().getConfigurationSection("Spawns").getKeys(false));


//		On lit les valeurs pour les generateurs dans le fichier config
		maxGoldDelay = getConfig().getInt("Generators.maxgolddelay");
		minGoldDelay = getConfig().getInt("Generators.mingolddelay");
		maxIronDelay = getConfig().getInt("Generators.maxirondelay");
		ticksToTry = getConfig().getInt("Generators.tickstotry");

//		Maintenant qu'on sait le nombre de couleurs qu'on a, on peut remplir les variables
		beds = new Boolean[colors.size()];
		healPools = new Boolean[colors.size()];
		Arrays.fill(beds, true);

		attackTagMilis = getConfig().getLong("attacktagstaytime");

		popupTowerSpeedMultiplier = getConfig().getInt("popuptowerspeedmultiplier");

		//		On a plus besoin de check pour citizen pck on l'utilise plus :L
////        Check si citizen 2.0 est present
////        ########  A REPARER ne detecte pas bien citizens pour l'instant :(  ##########################
//		if (getServer().getPluginManager().getPlugin("Citizens") == null || !getServer().getPluginManager().getPlugin("Citizens").isEnabled()) {
//			getLogger().log(Level.SEVERE, "Citizens 2.0 est requis mais n'as pas ete trouve ou n'etait pas encore active!");
//			getServer().getPluginManager().disablePlugin(this);
//			return;
//		}


//		On initialise les joueurs presents au cas ou il y en a
		for (Player player : Bukkit.getOnlinePlayers()) {
			utils.initPlayerMetadata(player);
		}

//        INITIALISATION DU GAMESTATE
		setGameState(GameState.WAITING);

//        Creation du lien entre Main et PlayersLiteners
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayersListeners(this), this);
		pm.registerEvents(new WorldListeners(this), this);
		pm.registerEvents(new NpcClickListener(this), this);

//        Juste pour être sur, on mets les mondes en pvp false et difficulty easy
		for (World w : Bukkit.getWorlds()) {
			w.setPVP(false);
			w.setDifficulty(Difficulty.EASY);
		}

//        Les commandes:
		getCommand("bedwars").setExecutor(new bedwarsCommand(this));
		getCommand("bedwars").setTabCompleter(new bedwarsCommandTab(this));
		getCommand("toggleparticules").setExecutor(new toggleparticulesCommand(this));


//		peut etre utiliser ca plus tard, ca permets de mettre des customs characters apparement
//		try {
//			CustomCharacters.ModifyAllowedCharacters();
//		} catch (NoSuchFieldException  e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}


//        On lit la limite de build de la map
		buildlimit = getConfig().getInt("buildlimit");
		builddownlimit = getConfig().getInt("builddownlimit");
		voidHeight = getConfig().getInt("voidheight");
		golemDamageMultiplier = getConfig().getDouble("golemdamagemultiplier");

//        Creation du trait ShopType
//		CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(ShopType.class).withName("ShopType"));
//		je vais essayer de faire mes propres npc au final :o


//		On regarde ou sont tout les npcs:
		if (getConfig().isConfigurationSection("npcs")) {
//		On creer les holograms
			for (String npc : getConfig().getConfigurationSection("npcs").getKeys(false)) {
				npcLocations.put(UUID.fromString(npc), new Location(Bukkit.getWorlds().get(0), getConfig().getDouble("npcs." + npc + ".x"), getConfig().getDouble("npcs." + npc + ".y"), getConfig().getDouble("npcs." + npc + ".z")));
				if (getConfig().getString("npcs." + npc + ".type").equalsIgnoreCase("shop")) {
					Hologram name = HologramsAPI.createHologram(this, new Location(Bukkit.getWorlds().get(0), getConfig().getDouble("npcs." + npc + ".x"), getConfig().getDouble("npcs." + npc + ".y") + 2.5, getConfig().getDouble("npcs." + npc + ".z")));
					name.appendTextLine(ChatColor.AQUA + "ITEM SHOP");
					name.appendTextLine(ChatColor.YELLOW + "" + ChatColor.BOLD + "RIGHT CLICK");
				}
				if (getConfig().getString("npcs." + npc + ".type").equalsIgnoreCase("upgrade")) {
					Hologram name = HologramsAPI.createHologram(this, new Location(Bukkit.getWorlds().get(0), getConfig().getDouble("npcs." + npc + ".x"), getConfig().getDouble("npcs." + npc + ".y") + 2.8, getConfig().getDouble("npcs." + npc + ".z")));
					name.appendTextLine(ChatColor.AQUA + "TEAM");
					name.appendTextLine(ChatColor.AQUA + "UPGRADES");
					name.appendTextLine(ChatColor.YELLOW + "" + ChatColor.BOLD + "RIGHT CLICK");
				}

			}
		}

		minBowCharge = (float) getConfig().getDouble("minbowcharge");

//		Le spawn
		spawn = new Location(Bukkit.getWorlds().get(0), getConfig().getDouble("mainspawn.x"), getConfig().getDouble("mainspawn.y"), getConfig().getDouble("mainspawn.z"), 0f, 0f);

//        MESSAGE D'ACTIVATION
		getServer().getConsoleSender().sendMessage(ChatColor.RED + getDescription().getName() + " v" + getDescription().getVersion() + ChatColor.GREEN + " activé!");

	}

	@Override
	public void onDisable() {
//        Message de désactivation
		getServer().getConsoleSender().sendMessage(ChatColor.RED + getDescription().getName() + " v" + getDescription().getVersion() + ChatColor.RED + " désactivé!");

		if (protocolManager != null)
			protocolManager.removePacketListeners(this);
//		Ca c'est un peu de code vole qui permet de reload sans probleme de protocollib
		try {
			Class<AttributeKey> attributeKeyClass = AttributeKey.class;
			Field namesField = attributeKeyClass.getDeclaredField("names");
			namesField.setAccessible(true);
			ConcurrentMap<String, Boolean> names = (ConcurrentMap) namesField.get((Object) null);
			Iterator var5 = names.keySet().iterator();

			while (var5.hasNext()) {
				String name = (String) var5.next();
				if (name.toLowerCase().startsWith("protocol")) {
					names.remove(name);
				}
			}
		} catch (Exception var6) {
			var6.printStackTrace();
		}

		if(npcManager!=null) {
			npcManager.removeAllNpcs(this);
		}

		for (Hologram holo : HologramsAPI.getHolograms(this)) {
			holo.delete();
		}

		for (Entity e : Bukkit.getWorlds().get(0).getEntities()) {
			if (e instanceof LivingEntity && !(e instanceof Player)) {
				e.remove();
			}
		}
	}

	//    Getters et setters du gamestate

	public void setGameState(GameState state) {
		this.gameState = state;
	}

	public boolean isState(GameState state) {
		return this.gameState == state;
	}


	public List<Player> getPlayers() {
		return players;
	}

	public GamePhase getGamePhase() {
		return gamePhase;
	}

	public void setGamePhase(GamePhase gamePhase) {
		this.gamePhase = gamePhase;
	}

	public Boolean hasBed(String color) {
		return beds[colors.indexOf(color)];
	}

	public void removeBed(String color) {
		this.beds[colors.indexOf(color)] = false;
	}

	public void StartStartTimer() {
		startTimer = new StartTimer(this, startTimer.timer);
		startTimer.runTaskTimer(this, 0, 20);
	}

	public List<TeamGen> getGens() {
		return gens;
	}

	public List<Player> getHiddenPlayers() {
		return hiddenPlayers;
	}


}
