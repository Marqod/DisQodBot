/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.disqodbot;

import java.util.Random;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 *
 * @author Marqod <eltharius@gmail.com>
 */
public class RollListener {
    
    Random rng = new Random();
    Roller roller = new Roller();
    
    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event) {
        try{
            
            IChannel channel = event.getMessage().getChannel();
            String message = event.getMessage().getContent();
            IUser user = event.getMessage().getAuthor();
            String sender = user.getName();
            
            ///Start of Work Area
            
            if (message.equalsIgnoreCase(">roll") || message.equalsIgnoreCase(">HELP")) {
                //Default explanation.
                sendMessage(channel, "To roll dice, type:");
                sendMessage(channel, ">roll NdM, for example: >roll 1d20");
                sendMessage(channel, "You can also add modifiers or different die types.");
                int roll = (rng.nextInt(19)+1);
                System.out.println("d20 roll: " + roll);
                sendMessage(channel, sender + "'s 1d20 roll: " + roll);

            } else if ((message).matches("^>roll ((\\d)+d((\\d)+|f)|advantage|disadvantage|dorgan)([\\+\\-](\\d)+d((\\d)+|f)){0,8}([\\+\\-](\\d+))?( .*)?")){
                //This is a proper dice roll.
                String alteredMessage = message.replace(">roll ","");
                alteredMessage = alteredMessage.toLowerCase();
                //Roll the dice.
                try{
                    String[] rollResult = roller.parseRollRequest(alteredMessage, sender);
                    sendMessage(channel, rollResult[0]);
                    //Check for crits
                    if (rollResult[1]!=null){
                        //TODO
                        sendMessage(channel, (sender + " rolled a natural 20!"));
                    }
                    if (rollResult[2]!=null) {
                        //TODO
                        sendMessage(channel, (sender + " rolled a natural 1!"));
                    }
                } catch (ExcessiveNumberOfDiceException ex) {
                    sendMessage(channel, (sender + ", you can't roll more than 100 dice at once."));
                }
            } else {
                if (message.startsWith(">roll")) {
                    //print usage.
                    sendMessage(channel, sender + ", invalid roll.");
                }
            }
            
            ///End of Work Area
            
            //Send roll.
        } catch (RateLimitException | MissingPermissionsException | DiscordException e) {
            System.out.println("Whoops.");
        }
    }
    
    void sendMessage(IChannel channel, String text) throws DiscordException, MissingPermissionsException,  RateLimitException{
        
        new MessageBuilder(QodBot.client).withChannel(channel).withContent(text).build();
    }
}
