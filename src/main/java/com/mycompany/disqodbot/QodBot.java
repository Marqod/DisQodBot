/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.disqodbot;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.InviteReceivedEvent;
import sx.blah.discord.handle.impl.obj.Invite;
import sx.blah.discord.util.DiscordException;

/**
 *
 * @author Marqod <eltharius@gmail.com>
 */
public class QodBot {
    static IDiscordClient client;
    
    public static void main(String[] args) throws DiscordException {
        
        if(args.length < 1){
            System.out.println("No token specified.");
        }
        
        String token = args[0];
                
        client = new ClientBuilder().withToken(token).login();
        client.getDispatcher().registerListener(new AnnotationListener());
        client.getDispatcher().registerListener(new RollListener());
        //Test.
        
        
        
    }
}
