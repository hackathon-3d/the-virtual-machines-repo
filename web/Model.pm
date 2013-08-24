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

}

1;
