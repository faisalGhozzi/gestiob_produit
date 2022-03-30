<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20220330153748 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE wishlist_transport DROP FOREIGN KEY FK_DCCB58BF9909C13F');
        $this->addSql('ALTER TABLE wishlist_transport DROP FOREIGN KEY FK_DCCB58BFA76ED395');
        $this->addSql('ALTER TABLE wishlist_transport ADD CONSTRAINT FK_DCCB58BF9909C13F FOREIGN KEY (transport_id) REFERENCES transport (id) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE wishlist_transport ADD CONSTRAINT FK_DCCB58BFA76ED395 FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE wishlist_transport DROP FOREIGN KEY FK_DCCB58BFA76ED395');
        $this->addSql('ALTER TABLE wishlist_transport DROP FOREIGN KEY FK_DCCB58BF9909C13F');
        $this->addSql('ALTER TABLE wishlist_transport ADD CONSTRAINT FK_DCCB58BFA76ED395 FOREIGN KEY (user_id) REFERENCES user (id)');
        $this->addSql('ALTER TABLE wishlist_transport ADD CONSTRAINT FK_DCCB58BF9909C13F FOREIGN KEY (transport_id) REFERENCES transport (id)');
    }
}
