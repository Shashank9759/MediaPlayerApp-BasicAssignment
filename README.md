# Media Player App

Welcome to the Media Player App! This project is a native Android application built using Kotlin. The app replicates the YouTube home screen UI and provides a media player to search, view, and interact with videos, similar to YouTube. Users can pause, resume, forward, and watch videos in fullscreen. The app uses Supabase DB, which works on PostgreSQL, for storing and retrieving video data.

## Features

- **YouTube-like Home Screen UI**: Browse video thumbnails similar to YouTube's home screen.
- **Search Functionality**: Search for videos using the search bar.
- **Video Playback**: Click on video thumbnails to navigate to a new screen and watch the video.
- **Playback Controls**: Pause, resume, and forward video playback.
- **Fullscreen Mode**: Watch videos in fullscreen.
- **Supabase DB**: Uses Supabase (PostgreSQL) for storing and managing video data.

## Technologies Used

- **Kotlin**: Programming language for Android development.
- **Android Studio**: Integrated development environment (IDE) for Android development.
- **Supabase**: Backend as a Service (BaaS) built on PostgreSQL.
- **PostgreSQL**: Open-source relational database management system.

## Installation

1. **Clone the repository**:

    ```bash
    git clone https://github.com/Shashank9759/MediaPlayerApp-BasicAssignment.git
    ```

2. **Open the project in Android Studio**:

    - Open Android Studio.
    - Click on "Open an existing Android Studio project".
    - Navigate to the cloned repository and open it.

3. **Set up Supabase**:

    - Sign up at [Supabase](https://supabase.io/).
    - Create a new project and set up your PostgreSQL database.
    - Note the API URL and public API key provided by Supabase.

4. **Configure Supabase in the app**:

    - In your project, locate the file where you initialize your database connection.
    - Add your Supabase API URL and public API key.

    ```kotlin
    val supabaseUrl = "YOUR_SUPABASE_URL"
    val supabaseKey = "YOUR_SUPABASE_KEY"
    ```

5. **Build the project**:

    - Click on "Build" in the menu bar.
    - Select "Rebuild Project" to ensure all dependencies are downloaded and the project is set up correctly.

6. **Run the app**:

    - Connect an Android device or start an emulator.
    - Click on the "Run" button or select "Run" from the menu bar.

## Contributing

Feel free to fork this repository and make changes. Pull requests are welcome!

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For any questions or feedback, please contact me at [shashankranjantech@gmail.com](mailto:shashankranjantech@gmail.com).

---

Enjoy your media playback experience!
