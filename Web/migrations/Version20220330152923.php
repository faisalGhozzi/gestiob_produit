<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20220330152923 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE wishlist_transport (id INT AUTO_INCREMENT NOT NULL, user_id INT DEFAULT NULL, transport_id INT DEFAULT NULL, INDEX IDX_DCCB58BFA76ED395 (user_id), INDEX IDX_DCCB58BF9909C13F (transport_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE wishlist_transport ADD CONSTRAINT FK_DCCB58BFA76ED395 FOREIGN KEY (user_id) REFERENCES user (id)');
        $this->addSql('ALTER TABLE wishlist_transport ADD CONSTRAINT FK_DCCB58BF9909C13F FOREIGN KEY (transport_id) REFERENCES transport (id)');
        $this->addSql('ALTER TABLE transport DROP FOREIGN KEY FK_66AB212EA76ED395');
        $this->addSql('ALTER TABLE transport DROP FOREIGN KEY FK_66AB212EBCF5E72D');
        $this->addSql('ALTER TABLE transport ADD CONSTRAINT FK_66AB212EA76ED395 FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE SET NULL');
        $this->addSql('ALTER TABLE transport ADD CONSTRAINT FK_66AB212EBCF5E72D FOREIGN KEY (categorie_id) REFERENCES categorie (id) ON DELETE CASCADE');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('DROP TABLE wishlist_transport');
        $this->addSql('ALTER TABLE transport DROP FOREIGN KEY FK_66AB212EBCF5E72D');
        $this->addSql('ALTER TABLE transport DROP FOREIGN KEY FK_66AB212EA76ED395');
        $this->addSql('ALTER TABLE transport ADD CONSTRAINT FK_66AB212EBCF5E72D FOREIGN KEY (categorie_id) REFERENCES categorie (id) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE transport ADD CONSTRAINT FK_66AB212EA76ED395 FOREIGN KEY (user_id) REFERENCES user (id) ON UPDATE CASCADE ON DELETE SET NULL');
    }
}
