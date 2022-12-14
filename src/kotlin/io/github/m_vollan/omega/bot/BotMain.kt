package io.github.m_vollan.omega.bot

import ch.qos.logback.classic.Logger
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.interactions.commands.Command
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData
import net.dv8tion.jda.api.interactions.commands.localization.LocalizationMap
import net.dv8tion.jda.api.interactions.commands.privileges.IntegrationPrivilege
import net.dv8tion.jda.api.requests.RestAction
import net.dv8tion.jda.api.requests.restaction.CommandEditAction

object BotMain {
    val bot_name = "Omega"
    lateinit var jda: JDA;
    lateinit var logger: Logger
    fun run()
    {
        logger.info("Adding slash commands")
        jda.addEventListener(SlashCommandListenerAdapter())
        jda.upsertCommand("ping", "Ping the bot").complete()
        jda.upsertCommand(Commands.slash("echo", "Repeats what you said")
            .addOption(OptionType.STRING, "message", "the message to say", true, false)
        ).complete()
        jda.upsertCommand(Commands.slash("give_role", "Gives a role to a user")
            .setGuildOnly(true)
            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_ROLES))
            .addOption(OptionType.ROLE,"role","The role to give", true, false)
            .addOption(OptionType.USER, "user", "The user to give a role to", true, false)
        ).complete()
        jda.upsertCommand(Commands.slash("give_all_role", "Give all members a role")
            .setGuildOnly(true)
            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_ROLES))
            .addOption(OptionType.ROLE, "role", "The role to give", true, false)
            .addOption(OptionType.ROLE, "role2", "1st restriction", false, false)
            .addOption(OptionType.ROLE, "role3", "2nd restriction", false, false)
            .addOption(OptionType.ROLE, "role4", "3rd restriction", false, false)
            .addOption(OptionType.ROLE, "role5", "4th restriction", false, false)
            .addOption(OptionType.BOOLEAN, "test", "Enable test mode to calculate without actually doing the role add", false, false)
        ).complete()
        jda.upsertCommand(Commands.slash("set", "Set a config value")
            .setGuildOnly(true)
            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_SERVER))
            .addSubcommands(
                SubcommandData("suggestion_channel", "Set the suggestion channel")
                    .addOption(OptionType.CHANNEL, "channel", "The channel to set as the suggestion channel", true, false)
            )
        ).complete()
        jda.upsertCommand(Commands.slash("lockdown", "Locks a channel")
            .setGuildOnly(true)
            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL))
            //.addOption(OptionType.CHANNEL, "channel", "The channel to lock down")
        ).complete()
        jda.upsertCommand(Commands.slash("unlock", "Unlocks a locked channel")
            .setGuildOnly(true)
            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL))
        ).complete()
        jda.upsertCommand(Commands.slash("lockdown_server", "Locks down the entire server")
            .setGuildOnly(true)
            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_SERVER))
            .addOption(OptionType.ROLE, "role", "The role that shouldn't be locked out", false, false)
        ).complete()
        jda.upsertCommand(Commands.slash("unlock_server", "Locks down the entire server")
            .setGuildOnly(true)
            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_SERVER))
            //.addOption(OptionType.ROLE, "role", "The role that shouldn't be locked out", false, false)
        ).complete()
        jda.upsertCommand(Commands.slash("color", "Set or create a user color role. If a user is not provided, this updates YOUR color")
            .setGuildOnly(true)
            //If you can manage roles, you can just create this manually anyways.
            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_ROLES))
            .addOption(OptionType.USER, "user", "The user who's role should be updated", false, false)
            .addOption(OptionType.STRING, "color", "The color to set. This should be in hex format.", true, false)
        ).complete()
        logger.info("Finished adding slash commands ")
    }

}