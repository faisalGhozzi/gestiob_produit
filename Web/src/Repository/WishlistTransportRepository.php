<?php

namespace App\Repository;

use App\Entity\WishlistTransport;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\ORM\OptimisticLockException;
use Doctrine\ORM\ORMException;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method WishlistTransport|null find($id, $lockMode = null, $lockVersion = null)
 * @method WishlistTransport|null findOneBy(array $criteria, array $orderBy = null)
 * @method WishlistTransport[]    findAll()
 * @method WishlistTransport[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class WishlistTransportRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, WishlistTransport::class);
    }

    /**
     * @throws ORMException
     * @throws OptimisticLockException
     */
    public function add(WishlistTransport $entity, bool $flush = true): void
    {
        $this->_em->persist($entity);
        if ($flush) {
            $this->_em->flush();
        }
    }

    /**
     * @throws ORMException
     * @throws OptimisticLockException
     */
    public function remove(WishlistTransport $entity, bool $flush = true): void
    {
        $this->_em->remove($entity);
        if ($flush) {
            $this->_em->flush();
        }
    }

    // /**
    //  * @return WishlistTransport[] Returns an array of WishlistTransport objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('w')
            ->andWhere('w.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('w.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?WishlistTransport
    {
        return $this->createQueryBuilder('w')
            ->andWhere('w.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
}
