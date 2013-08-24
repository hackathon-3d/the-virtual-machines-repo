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
      'CREATE TABLE users (id       INTEGER      NOT NULL PRIMARY KEY AUTO_INCREMENT,
                           email    VARCHAR(254) UNIQUE NOT NULL,
                           pwhash   VARCHAR(256) NOT NULL,
                           nickname VARCHAR(64)  NOT NULL);'
   );

   $class->db->query(
      "INSERT INTO users(email, pwhash, nickname) VALUES('foo\@bar.com', 'asdf', 'John Doe');"
   );
}

1;
