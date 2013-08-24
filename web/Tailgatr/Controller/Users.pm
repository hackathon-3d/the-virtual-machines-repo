package Tailgatr::Controller::Users;

use strict;
use warnings;

use Data::UUID;
use Digest::SHA qw/sha256_hex/;
use base 'Mojolicious::Controller';

sub get {
   my $self = shift;

   $self->render(json => 
      Tailgatr::Model::User->select({
         id => $self->param('id')
      })->hashes
   );
}

sub getAll {
   my $self = shift;

   $self->render(json => 
      Tailgatr::Model::User->select({})->hashes
   );
}

sub create {
   my $self = shift;

   my %json = %{$self->req->json};

   my $ug = new Data::UUID;
   
   my %user = ( 
      email  => $json{email},
      name   => $json{name},
      pwhash => sha256_hex($json{password}),
      token  => $ug->create_str()
   );

   my $check_user = Tailgatr::Model::User->select({email => $user{email}})->hash();
   if ($check_user->{id}) {
      $self->render(
         text   => "That email address is already in use",
         status => 409,
      );
      return;
   }

   my $uid = Tailgatr::Model::User->insert({%user});

   $self->render(
      json   => scalar Tailgatr::Model::User->select({id => $uid})->hash,
      status => 201,
   );
}

1;
