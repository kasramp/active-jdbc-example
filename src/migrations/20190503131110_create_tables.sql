CREATE TABLE IF NOT EXISTS users (
  id INTEGER DEFAULT NULL AUTO_INCREMENT PRIMARY KEY,
  email_address VARCHAR(512) NOT NULL,
  first_name VARCHAR(512) NOT NULL,
  last_name VARCHAR(512) NOT NULL,
  CONSTRAINT uc_user_email_address UNIQUE(email_address),
  INDEX idx_user_email_address (email_address)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS songs (
  id INTEGER AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(512) NOT NULL,
  artist VARCHAR(512) NOT NULL,
  user_id INTEGER NOT NULL,
  FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS playlists (
  id INTEGER AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(512) NOT NULL,
  user_id INTEGER NOT NULL,

  CONSTRAINT uc_playlist_name UNIQUE(name),
  FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS playlist_song (
  id INTEGER AUTO_INCREMENT PRIMARY KEY,
  playlist_id INTEGER NOT NULL,
  song_id INTEGER NOT NULL,

  CONSTRAINT fk_playlist_song_playlist_id FOREIGN KEY (playlist_id) REFERENCES playlists (id),
  CONSTRAINT fk_playlist_song_song_id FOREIGN KEY (song_id) REFERENCES songs (id)
) engine=InnoDB;