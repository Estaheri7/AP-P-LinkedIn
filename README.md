# FakedIn Application

## Overview

This project is the server-side implementation of a LinkedIn-like application developed as part of the advanced programming course at Amirkabir University. It uses the MVC design pattern and a MySQL database, and is written using Java HttpServer.

## Features

### User Management
- **User Signup**: Create a new user account (`/signup`).
- **User Login**: Authenticate and login a user (`/login`).

### User Profile
- **View User Profile**: Retrieve user profile by email (`/user`).
- **View All Users**: Retrieve all user profiles (`/users`).
- **View User Skills**: Retrieve user skills by email (`/user/skill`).
- **View User Education**: Retrieve the latest education entry for a user by email (`/user/education`).
- **View All User Educations**: Retrieve all education entries for a user by email (`/user/educations`).
- **View User Contact Information**: Retrieve user contact information by email (`/user/contact`).
- **Update User Profile**: Update user profile information (`/profile/update`).
- **Update User Skills**: Update user skills (`/profile/update/skill`).
- **Add User Education**: Add a new education entry for a user (`/profile/add/education`).
- **Update User Education**: Update an existing education entry for a user (`/profile/update/education`).
- **Update User Contact Information**: Update user contact information (`/profile/update/contact`).
- **Change Contact Visibility**: Change the visibility of user contact information (`/profile/contact/visibility`).

### Media Management
- **View User Avatar**: Retrieve user avatar by email (`/user/avatar`).
- **View User Background**: Retrieve user background by email (`/user/background`).
- **Update User Avatar**: Update user avatar (`/upload-avatar`).
- **Update User Background**: Update user background (`/upload-background`).
- **View Post Media**: Retrieve media for a specific post by post ID (`/post/media`).
- **Add Media to Post**: Add media to an existing post (`/posts/add-media`).
- **Send Media in Chat**: Send media in chat (`/send-file`).
- **View Chat Media**: Retrieve chat media by unique file identifier (`/chat-media`).

### Posts and Feeds
- **View All Posts**: Retrieve all posts in the feed (`/posts/feeds`).
- **View Post by ID**: Retrieve a specific post by post ID (`/post`).
- **View User Posts**: Retrieve all posts made by a user by email (`/posts`).
- **View Last Post**: Retrieve the latest post made by a user (`/lastPost`).
- **Add Post**: Create a new post (`/posts/add`).
- **Update Post**: Update an existing post (`/posts/update`).
- **Delete Post**: Delete an existing post (`/posts/delete`).

### Post Interactions
- **Like a Post**: Like a post by post ID (`/posts/like`).
- **Dislike a Post**: Dislike a post by post ID (`/posts/dislike`).
- **Check if Post is Liked**: Check if a post is liked by post ID (`/posts/isLiked`).
- **View Post Likes**: Retrieve all likes for a post by post ID (`/posts/likes`).

### Comments
- **View Post Comments**: Retrieve all comments for a post by post ID (`/posts/comments`).
- **Add Comment to Post**: Add a comment to a post by post ID (`/posts/add-comment`).
- **Update Comment**: Update an existing comment by comment ID (`/posts/update-comment`).
- **Delete Comment**: Delete an existing comment by comment ID (`/posts/delete-comment`).

### Search
- **Search Users**: Search users based on a query (`/search`).
- **Search Users by Skill**: Search users based on skill (`/search/skill`).

### Connections
- **Check Follow Status**: Check if a user follows another user by email (`/hasFollowed`).
- **View Followers**: Retrieve all followers for a user by email (`/followers`).
- **View Followings**: Retrieve all followings for a user by email (`/followings`).
- **Follow User**: Follow a user by email (`/follow`).
- **Unfollow User**: Unfollow a user by email (`/unfollow`).

### Connections and Networking
- **View Connections**: Retrieve all connections for a user by email (`/connections`).
- **View Received Connection Requests**: Retrieve all received connection requests for a user (`/connections/receiver`).
- **View Sent Connection Requests**: Retrieve all sent connection requests for a user (`/connections/sender`).
- **View Pending Connections**: Retrieve all pending connections for a user (`/connections/pending`).
- **Check if Connection is Accepted**: Check if a connection is accepted (`/connect/isAccepted`).
- **Send Connection Request**: Send a connection request to another user (`/send-connect`).
- **Accept Connection Request**: Accept a connection request by receiver email and sender query email (`/accept-connection`).
- **Decline Connection Request**: Decline a connection request by receiver email and sender query email (`/decline-connection`).

### Messaging
- **View Chats**: Retrieve all chats for a user (`/chats`).
- **View Chat with User**: Retrieve chat with a specific user by email (`/user/chat`).
- **Send Message**: Send a message to a user (`/send-message`).

### Notifications
- **View Notifications**: Retrieve all notifications for a user by email (`/notifications`).
- **Add Notification**: Add a new notification (`/add-notification`).

## Technologies Used

- Java HttpServer
- MVC Design Pattern
- MySQL Database

## Installation

### Prerequisites

- Java Development Kit (JDK) installed
- MySQL Server installed and running

### Step 1: Clone the Repository

First, clone the repository to your local machine:

```bash
git clone https://github.com/Estaheri7/AP-P-LinkedIn.git
cd your-repository
```

### Step 2: Configure the Database Connection

1. Open the `SQLConnection.java` file located at `src/com/example/server/database_conn/`.
2. Update the following fields with your MySQL server details:

```java
private static final String URL = "jdbc:mysql://host:port/database";
private static final String USER = "username";
private static final String PASSWORD = "password";
```

Replace `host`, `port`, `database`, `username`, and `password` with your MySQL server details.

