<?php

namespace App\Entity;

use App\Repository\CategorieRepository;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * @ORM\Entity(repositoryClass=CategorieRepository::class)
 * @UniqueEntity("nom")
 */
class Categorie
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer", nullable=true)
     * @Groups("categorie")
     */
    private $id;

    /**
     * @Assert\NotBlank(message="Veuillez remplir tous les champs")
     * @ORM\Column(type="string", length=255, nullable=true)
     * @Groups("categorie")
     */
    private $nom;

    /**
     * @Assert\NotBlank(message="Veuillez remplir tous les champs")
     * @ORM\Column(type="string", length=255, nullable=true)
     * @Groups("categorie")
     */
    private $boitevitesse;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;

        return $this;
    }

    public function getBoiteVitesse(): ?string
    {
        return $this->boitevitesse;
    }

    public function setBoiteVitesse(?string $boitevitesse): self
    {
        $this->boitevitesse = $boitevitesse;

        return $this;
    }
}
