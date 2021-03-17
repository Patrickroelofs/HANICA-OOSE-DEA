use spotitube;

insert into users(name, username, password) values ('Patrick Roelofs', 'patrick', '112112');

insert into playlists(id, name, owner) values
(1, 'playlist1', 'patrick'),
(2, 'playlist2', 'patrick');

INSERT INTO tracks (id, title, performer, duration, album, playcount, publicationDate, description, offlineAvailable) VALUES
(1, 'Blinding Lights', 'The Weeknd', 200, 'After Hours', 324, '23-03-2020', 'Song about the weekend', 0),
(2, 'Heartless', 'The Weeknd', 198, 'After Hours', 257, '23-03-2020', 'Song about the heartless people', 0),
(3, 'In Your Eyes', 'The Weeknd', 238, 'After Hours', 99, '23-03-2020', 'Song about your eyes', 0);

INSERT INTO playlists_tracks (playlistId, trackId, offlineAvailable) VALUES
(1, 1, 0),
(2, 2, 1),
(2, 3, 1);