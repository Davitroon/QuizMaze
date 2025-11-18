package logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import ui.ChooseMazeUI;
import ui.MazeManagementUI;

public class Login {

	private static String username;
	
	private Model model;
	private ChooseMazeUI chooseMaze;
	private MazeManagementUI mazeManagement;
	private Player player;
	
	public Login(Model model) {
		this.model = model;
	}
	

	public String getUsername() {
		return username;
	}

	
	public void login() {
		Scanner scn = new Scanner(System.in);
		boolean accessGranted = false;
		int options = 0;
		
		while (!accessGranted) {
			System.out.println("Choose an option: \n1 - Create User \n2 - Log In \n0 - Exit");
			if (scn.hasNextInt()) {
				options = scn.nextInt();
				scn.nextLine();
				
				switch (options) {
				case 1:
					System.out.print("New username: ");
					String newUser = scn.nextLine();

					if (!validateUsername(newUser)) {
					    break; 
					}
					
					System.out.print("Password: ");
					String newPassword = scn.nextLine();
					
					if (!validatePassword(newPassword)) {
					    break; 
					}

					System.out.print("Repeat password: ");
					String repeatedPassword = scn.nextLine();
					
					if (!validatePassword(repeatedPassword)) {
					    break; 
					}
					
					if (!repeatedPassword.equals(newPassword)) {
						System.out.println("Passwords do not match.");
						
					} else {
						int playerId = model.insertUser(newUser, repeatedPassword);

						if (playerId != -1) {
						    player = new Player(newUser);
						    player.setId(playerId);

						    System.out.println("User " + newUser + " created with ID: " + playerId);

						    if (chooseMaze == null) {
						        chooseMaze = new ChooseMazeUI(this, model, player);
						    }
						    chooseMaze.loadMazes();
						    chooseMaze.setVisible(true);

						    accessGranted = true;
						} else {
						    System.out.println("Error creating user.");
						}
					}
				break;

				case 2:
				    System.out.print("Username: ");
				    username = scn.nextLine();
				    
					if (!validateUsername(username)) {
					    break; 
					}

				    System.out.print("Password: ");
				    String password = scn.nextLine();
				    
					if (!validatePassword(password)) {
					    break; 
					}

				    ResultSet rs = model.queryUser(username, password);

				    try {
				        if (rs.next()) {
				            int userId = rs.getInt("id");
				            String user = rs.getString("name");

				            player = new Player(user);
				            player.setId(userId);

				            if (user.equals("admin")) {
				                if (mazeManagement == null) {
				                    mazeManagement = new MazeManagementUI(this, model);
				                }
				                mazeManagement.updateMazes();
				                mazeManagement.setVisible(true);

				            } else {
				                if (chooseMaze == null) {
				                    chooseMaze = new ChooseMazeUI(this, model, player);
				                }
				                chooseMaze.loadMazes();
				                chooseMaze.setVisible(true);
				            }

				            accessGranted = true;

				        } else {
				            System.out.println("Incorrect username or password.");
				        }

				    } catch (SQLException e) {
				        System.out.println("Error while logging in.");
				        e.printStackTrace();
				    }
				    break;
				
				case 0:
					System.out.println("Exiting program...");
					System.exit(1);
					
				default:
					System.out.println("Error: Invalid option.");
				}
				
			} else {
				System.out.println("Error: enter a valid value. ");
				scn.nextLine();
			}
		}
	}
	
	private boolean validatePassword(String password) {
	    if (password == null || password.length() < 4) {
	        System.out.println("Password must be at least 4 characters long.");
	        return false;
	    }
	    return true;
	}

	private boolean validateUsername(String name) {
	    if (name == null || name.trim().isEmpty()) {
	        System.out.println("Username cannot be empty.");
	        return false;
	    }
	    if (!name.matches("^[a-zA-Z0-9_]{3,20}$")) {
	        System.out.println("Username must be between 3 and 20 characters and can only contain letters, numbers, and underscores.");
	        return false;
	    }
	    return true;
	}
}
