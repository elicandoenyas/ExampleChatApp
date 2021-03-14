# ExampleChatApp

This app introduces a simple chat app, where the user can enter a nickname and fetch a set of messages.

Then the user has the option to send a new message, which they can view on the chat screen. However, the sent messages are not uploaded to a server.

The user nickname is persisted. So if the user enters their nickname as 'Joe Applesend', and then quits the app, 
the next time they open the app, the welcome screen is passed and directly the chat screen opens up with the nickname 'Joe Applesend'

This project uses MVVM pattern.

It uses Glide library to fetch the avatar image of users.

It uses Retrofit library to fetch the messages from server.

It uses Gson library to parse the response.
