USE diceGameTest;

INSERT INTO `diceGameTest`.`players` (`id`, `register_date`, `success_rate`, `username`) VALUES ('1', '2024-02-06 11:18:57.666483', '100.00', 'maya');
INSERT INTO `diceGameTest`.`players` (`id`, `register_date`, `success_rate`, `username`) VALUES ('2', '2024-02-06 11:19:05.845846', '33.33', 'sandy');
INSERT INTO `diceGameTest`.`players` (`id`, `register_date`) VALUES ('3', '2024-02-06 11:19:12.331906');
INSERT INTO `diceGameTest`.`players` (`id`, `register_date`, `success_rate`, `username`) VALUES ('4', '2024-02-06 11:19:17.716334', '50.00', 'javi');

INSERT INTO `diceGameTest`.`games` (`game_id`, `dice1`, `dice2`, `played_time`, `player_entity_id`) VALUES ('1', '1', '6', '2024-02-06 12:18:57.666483', '1');
INSERT INTO `diceGameTest`.`games` (`game_id`, `dice1`, `dice2`, `played_time`, `player_entity_id`) VALUES ('2', '3', '5', '2024-02-06 12:20:57.666483', '2');
INSERT INTO `diceGameTest`.`games` (`game_id`, `dice1`, `dice2`, `played_time`, `player_entity_id`) VALUES ('3', '2', '6', '2024-02-06 12:21:57.666483', '2');
INSERT INTO `diceGameTest`.`games` (`game_id`, `dice1`, `dice2`, `played_time`, `player_entity_id`) VALUES ('4', '3', '4', '2024-02-06 12:24:57.666483', '2');
INSERT INTO `diceGameTest`.`games` (`game_id`, `dice1`, `dice2`, `played_time`, `player_entity_id`) VALUES ('5', '4', '4', '2024-02-06 12:25:57.666483', '4');
INSERT INTO `diceGameTest`.`games` (`game_id`, `dice1`, `dice2`, `played_time`, `player_entity_id`) VALUES ('6', '2', '5', '2024-02-06 12:30:57.666483', '4');

