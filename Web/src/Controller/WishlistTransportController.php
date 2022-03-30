<?php

namespace App\Controller;

use App\Entity\Transport;
use App\Entity\User;
use App\Entity\WishlistTransport;
use Exception;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Serializer\Exception\ExceptionInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Routing\Annotation\Route;


class WishlistTransportController extends AbstractController
{
    // JSON RESPONSES

    /**
     * @Route("/wishlist/json/user/new", name="newWishlistTransportJson", methods={"POST"})
     * @throws Exception
     */
    public function newWishlistTransportJson(Request $request): JsonResponse
    {
        $wishlistTransport = new WishlistTransport();

        $em = $this->getDoctrine()->getManager();

        $user = $this->getDoctrine()->getRepository(User::class)->find($request->get('user'));
        $transport = $this->getDoctrine()->getRepository(Transport::class)->find($request->get('transport'));

        $wishlistTransport->setUser($user);
        $wishlistTransport->setTransport($transport);

        $em->persist($wishlistTransport);
        $em->flush();

        return new JsonResponse($wishlistTransport);
    }

    /**
     * @Route("/wishlist/json/user/{id}", name="WishlistTransportJsonAction")
     * @throws ExceptionInterface
     */
    public function wishlistTransport($id): Response
    {
        $user = $this->getDoctrine()->getManager()->getRepository(User::class)->find($id);
        $wishlistTransport = $this->getDoctrine()->getManager()
            ->getRepository(WishlistTransport::class)->findBy(['user' => $user]);

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($wishlistTransport);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/wishlist/json/{id}", name="WishlistTransportIdJson")
     * @throws ExceptionInterface
     */
    public function transportIdJson($id): JsonResponse
    {
        $wishlistTransport = $this->getDoctrine()->getManager()
            ->getRepository(WishlistTransport::class)->find($id);

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($wishlistTransport);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/wishlist/json/delete/{id}", name="deleteWishlistTransportJsonAction")
     * @throws ExceptionInterface
     */
    public function deleteTransportJsonAction($id): JsonResponse
    {
        $wishlistTransport = $this->getDoctrine()
            ->getRepository(WishlistTransport::class)->find($id);
        $this->getDoctrine()->getManager()->remove($wishlistTransport);
        $this->getDoctrine()->getManager()->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($wishlistTransport);
        return new JsonResponse($formatted);
    }

    // JSON RESPONSES DONE !!!

}