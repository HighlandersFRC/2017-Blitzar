package org.usfirst.frc.team4499.robot.tools;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.command.Command;

public class CMDGroup {
	public ArrayList<Command> commands = new ArrayList<Command>();
	
	
	public CMDGroup(){
		
	}
	public void add(Command cmd){
		commands.add(cmd);
	}
	public void cancleAll(){
		for(Command cmd: commands){
			cmd.cancel();
		}
	}
	public void startAll(){
		for(Command cmd: commands){
			cmd.start();
		}
	}
}
