package com.natem135.deathbans.utils;

import java.util.Random;

public class DeathMessageGenerator {

    private static final Random random = new Random(System.currentTimeMillis());

    private static final String[] deathMessages = {
            "Thou hast fallen and lost thy life, unbeknowst to thee the name of the thief. Thou hast lost thy wager and becomest but a cog to the strong. So thy rehabilitation endeth incomplete, and thy baseless indignity shall become reality...",
            "Thou hast fallen and lost thy noble life. The hero hath crumbled over the weight of his own justice. His story of revolution reacheth not the ears of the people. The torch of courage that hath begun to kindle hath now been extinguished by tainted winds.",
            "Thou hast fallen, thy soul reduced to ashes. The blazing defiance that once guided thee hath been quenched by a mirage's maelstrom. And thus, the hero meeteth his end, his passing marked by a dying flame's vigil..."
    };

    public static String getDeathMessage() {
        return deathMessages[random.nextInt(deathMessages.length)]+ "\n\nYou have been death-banned.";
    }
}