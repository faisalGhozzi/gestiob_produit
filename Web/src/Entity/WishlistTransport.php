<?php

namespace App\Entity;

use App\Repository\WishlistTransportRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=WishlistTransportRepository::class)
 */
class WishlistTransport
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\ManyToOne(targetEntity=User::class)
     * @ORM\JoinColumns({
     *  @ORM\JoinColumn(name="user_id",referencedColumnName="id", onDelete="CASCADE")
     *})
     */
    private $user;

    /**
     * @ORM\ManyToOne(targetEntity=Transport::class)
     * * @ORM\JoinColumns({
     *  @ORM\JoinColumn(name="transport_id",referencedColumnName="id", onDelete="CASCADE")
     *})
     */
    private $transport;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getUser(): ?User
    {
        return $this->user;
    }

    public function setUser(?User $user): self
    {
        $this->user = $user;

        return $this;
    }

    public function getTransport(): ?Transport
    {
        return $this->transport;
    }

    public function setTransport(?Transport $transport): self
    {
        $this->transport = $transport;

        return $this;
    }
}
