/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.disqodbot;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.InviteReceivedEvent;
import sx.blah.discord.handle.obj.IInvite;

/**
 *
 * @author Marqod <eltharius@gmail.com>
 */
public class InviteListener {
    @EventSubscriber
    public void onReady(InviteReceivedEvent event) throws Exception {
            IInvite[] invite = event.getInvites();
            invite[0].accept();
    }
}
