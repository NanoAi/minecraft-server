public static PlayerStats getPlayerStats( Player ply ) {
    if ( !ply.isValid() ) return null;

    PlayerStats plyStats = new PlayerStats();

    try {
        double armour = Objects.requireNonNull( ply.getAttribute( Attribute.GENERIC_ARMOR ) ).getValue();
        double toughness = Objects.requireNonNull( ply.getAttribute( Attribute.GENERIC_ARMOR ) ).getValue();


        plyStats.health = ply.getHealth();
        plyStats.maxHealth = Objects.requireNonNull( ply.getAttribute( Attribute.GENERIC_MAX_HEALTH ) ).getValue();
        plyStats.dmgReduction = 1 - ( 1 - Math.min( 20, Math.max( armour/5, armour - (4 / (toughness + 8)) ) ) / 25 );

        return plyStats;
    } catch (NullPointerException e){
        return null;
    }
}

private static String formatStats(PlayerStats stats) {
    final String str = "HP: %d/%d ------ RES: %d";
    return String.format( str, (int) stats.health, (int) stats.maxHealth, (int) stats.dmgReduction );
}
    
new BukkitRunnable() {
    @Override
    public void run() {
        // TODO Implement ProtocolLib, create a HUD handler for Forge.
        Bukkit.getOnlinePlayers().forEach( (ply) -> {
            PlayerStats stats = getPlayerStats( ply );
            if ( stats != null ) {
                ply.sendActionBar( ChatColor.GRAY + formatStats( stats ) );
            }
        } );
    }
}.runTaskTimer( this, 0L, 12L );
