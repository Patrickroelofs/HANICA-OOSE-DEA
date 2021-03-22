use spotitube;

insert into users(name, username, password) values ('Patrick Roelofs', 'patrick', '112112');

insert into playlists(id, name, owner) values
(1, 'playlist1', 'patrick'),
(2, 'playlist2', 'patrick');

INSERT INTO tracks (id, title, performer, duration, album, playcount, publicationDate, description, offlineAvailable) VALUES
(1, 'Goliath', 'WOODKID', 351, 'S16', 324, '23-03-2020', 'Goliath from woodkid', 0),
(2, 'Pale Yellow', 'WOODKID', 410, 'S16', 257, '23-03-2020', 'Pale Yellow from woodkid', 0),
(3, 'Enemy', 'WOODKID', 405, 'S16', 99, '23-03-2020', 'Enemy from woodkid', 0);

INSERT INTO playlists_tracks (playlistId, trackId, offlineAvailable) VALUES
(1, 1, 0),
(2, 2, 1),
(2, 3, 1);