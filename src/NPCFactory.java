import java.util.List;
import java.util.Map;

public class NPCFactory {
    public static NPC createNPC(GamePanel gamePanel, NPCType npcType) {
        return switch (npcType) {
            case REGULAR_KID -> new NPC(
                    gamePanel,
                    npcType,
                    List.of(
                            new Statement("Hey, man! I'm just a regular ol' kid"),
                            new Statement("Mama, says I'm a good kid!"),
                            new Statement("I love halloween!"),
                            new Statement("I hear that the ghost kid might have the blue candy")
                    ),
                    Map.of(
                            "left1", Util.getBufferedImage("npc/regularKid/left1.png"),
                            "left2", Util.getBufferedImage("npc/regularKid/left2.png"),
                            "right1", Util.getBufferedImage("npc/regularKid/right1.png"),
                            "right2", Util.getBufferedImage("npc/regularKid/right2.png"),
                            "up1", Util.getBufferedImage("npc/regularKid/up1.png"),
                            "up2", Util.getBufferedImage("npc/regularKid/up2.png"),
                            "down1", Util.getBufferedImage("npc/regularKid/down1.png"),
                            "down2", Util.getBufferedImage("npc/regularKid/down2.png")
                    )
            );
            case ZOMBIE_KID -> new NPC(
                    gamePanel,
                    npcType,
                    List.of(
                            new Statement("Braaaaaainzzzz, just kidding!"),
                            new Statement("Rahhhhhh!"),
                            new Statement("Zombieeee!"),
                            new Statement("I hear that the ghost kid might have the blue candy")
                    ),
                    Map.of(
                            "left1", Util.getBufferedImage("npc/zombie/left1.png"),
                            "left2", Util.getBufferedImage("npc/zombie/left2.png"),
                            "right1", Util.getBufferedImage("npc/zombie/right1.png"),
                            "right2", Util.getBufferedImage("npc/zombie/right2.png"),
                            "up1", Util.getBufferedImage("npc/zombie/up1.png"),
                            "up2", Util.getBufferedImage("npc/zombie/up2.png"),
                            "down1", Util.getBufferedImage("npc/zombie/down1.png"),
                            "down2", Util.getBufferedImage("npc/zombie/down2.png")
                    )
            );
            case GHOST_KID -> new NPC(
                    gamePanel,
                    npcType,
                    List.of(
                            new Statement("OoooOooO, Spooookyyy!"),
                            new Statement("I'm a ghoooooosttt!"),
                            new Statement("I'm so spooookyyyyy!"),
                            new Statement("Just kidding, haha. Take this blue piece of candy.")
                    ),
                    Map.of(
                            "left1", Util.getBufferedImage("npc/ghost/left1.png"),
                            "left2", Util.getBufferedImage("npc/ghost/left2.png"),
                            "right1", Util.getBufferedImage("npc/ghost/right1.png"),
                            "right2", Util.getBufferedImage("npc/ghost/right2.png"),
                            "up1", Util.getBufferedImage("npc/ghost/up1.png"),
                            "up2", Util.getBufferedImage("npc/ghost/up2.png"),
                            "down1", Util.getBufferedImage("npc/ghost/down1.png"),
                            "down2", Util.getBufferedImage("npc/ghost/down2.png")
                    )
            );

        };
    }
}
