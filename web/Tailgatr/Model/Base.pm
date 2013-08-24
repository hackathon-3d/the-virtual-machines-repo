package Tailgatr::Model::Base;

use strict;
use warnings;
use base qw/Mojo::Base/;

sub select {
   my $class = shift;
   my @columns = qw/id email name token/;
   Tailgatr::Model->db->select($class->table_name, \@columns, @_);
}

sub insert {
   my $class = shift;
   my $db = Tailgatr::Model->db;
   $db->insert($class->table_name, @_) or die $db->error();
   $db->last_insert_id('','','','','') or die $db->error();
}

sub update {
   my $class = shift;
   my $db = Tailgatr::Model->db;
   $db->update($class->table_name, @_) or die $db->error();
}

sub delete {
   my $class = shift;
   my $db = Tailgatr::Model->db;
   $db->delete($class->table_name, @_) or die $db->error();
}

1;
__END__
