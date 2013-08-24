package Tailgatr::Model;

use strict;
use warnings;
use DBIx::Simple;
use SQL::Abstract;
use Carp qw/croak/;
use Mojo::Loader;

my $modules = Mojo::Loader->search('Tailgatr::Model');
for my $m (@$modules) {
   Mojo::Loader->load($m);
}

my $DB;

sub init {
   my ($class, $config) = @_;
   croak "No DSN passed" unless $config && $config->{dsn};

   unless ($DB) {
      $DB = DBIx::Simple->connect(@$config{qw/dsn user password/},
         {
            RaiseError         => 1,
            PrintError         => 0,
            ShowErrorStatement => 1
         }) or die DBIx::Simple->error;

      $DB->abstract = SQL::Abstract->new(
         convert => 'upper',
         case    => 'lower',
         logic   => 'and'
      );


     unless (eval { $DB->select('users')}) {
        $class->generate_ddl();
     }

     return $DB;
   }
}

sub db {
   return $DB if $DB;
   croak "init() must be called first";
}

sub generate_ddl {
   my $class = shift;

   $class->db->query(
      'CREATE TABLE users  (id          INTEGER      NOT NULL PRIMARY KEY AUTO_INCREMENT,
                            email       VARCHAR(254) UNIQUE NOT NULL,
                            pwhash      VARCHAR(256) NOT NULL,
                            token       VARCHAR(32),
                            name        VARCHAR(64)  NOT NULL);'
   );
      
   $class->db->query(
      'CREATE TABLE events (id          INTEGER      NOT NULL PRIMARY KEY AUTO_INCREMENT,
                            title       VARCHAR(255) UNIQUE NOT NULL,
                            owner_id    INTEGER      NOT NULL,
                            description TEXT,
                            date        DATETIME,
                            FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE);'
   );
   
   $class->db->query(
      'CREATE TABLE items   (id         INTEGER      NOT NULL PRIMARY KEY AUTO_INCREMENT,
                            title       VARCHAR(255) UNIQUE NOT NULL);'
   );

   $class->db->query(
      'CREATE TABLE event_invitees (event_id INTEGER NOT NULL,
                                    user_id  INTEGER NOT NULL,
                                    FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
                                    FOREIGN KEY (user_id)  REFERENCES users(id)  ON DELETE CASCADE,
                                    PRIMARY KEY (event_id, user_id));'
   );

   $class->db->query(
      'CREATE TABLE event_items    (event_id INTEGER NOT NULL,
                                    item_id  INTEGER NOT NULL,
                                    user_id  INTEGER,
                                    FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
                                    FOREIGN KEY (item_id)  REFERENCES items(id)  ON DELETE CASCADE,
                                    FOREIGN KEY (user_id)  REFERENCES users(id)  ON DELETE CASCADE,
                                    PRIMARY KEY (event_id, item_id));'
   );

}

1;
